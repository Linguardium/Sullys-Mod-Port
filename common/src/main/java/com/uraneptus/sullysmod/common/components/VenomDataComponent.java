package com.uraneptus.sullysmod.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.uraneptus.sullysmod.SullysMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// TODO: potentially use effect instances instead of effects directly
public record VenomDataComponent(@Nullable MobEffectInstance beneficial, @Nullable MobEffectInstance harmful) implements TooltipProvider {
    public static final VenomDataComponent EMPTY = new VenomDataComponent(null, null);
    private static final Codec<Holder<MobEffect>> VALIDATED_MOB_EFFECT_CODEC = MobEffect.CODEC.validate(VenomDataComponent::validateBoundEffect);
    public static MapCodec<VenomDataComponent> CODEC = RecordCodecBuilder.mapCodec(instance->instance.group(
            MobEffectInstance.CODEC.optionalFieldOf("BeneficialEffect", null).forGetter(VenomDataComponent::beneficial),
            MobEffectInstance.CODEC.optionalFieldOf("HarmfulEffect", null).forGetter(VenomDataComponent::harmful)
    ).apply(instance, VenomDataComponent::new));

    public static StreamCodec<RegistryFriendlyByteBuf, VenomDataComponent> PACKET_CODEC = StreamCodec.composite(
            MobEffectInstance.STREAM_CODEC, VenomDataComponent::beneficial,
            MobEffectInstance.STREAM_CODEC, VenomDataComponent::harmful,
            VenomDataComponent::new);

    private static DataResult<Holder<MobEffect>> validateBoundEffect(Holder<MobEffect> effect) {
        if (effect == null) return DataResult.success(null);
        Holder<MobEffect> referenceEffect = switch(effect.kind()) {
            case net.minecraft.core.Holder.Kind.REFERENCE -> effect;
            case net.minecraft.core.Holder.Kind.DIRECT -> {
                ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(effect.value());
                if (id == null) yield null;
                yield BuiltInRegistries.MOB_EFFECT.get(id).map(lookupEffect-> {
                    if (lookupEffect.value().equals(effect.value())) return lookupEffect;
                    return null;
                }).orElse(null);
            }
        };
        if (referenceEffect != null) return DataResult.success(referenceEffect);
        return DataResult.error(()->"Invalid mob effect in venom data: {}", effect);
    }

    public VenomDataComponent withBeneficial(@Nullable MobEffectInstance effect) {
        if (effect == null) return withoutBeneficial();
        else if (effect.getEffect().kind() != Holder.Kind.REFERENCE) {
            SullysMod.LOGGER.error("Attempted to set beneficial effect to incomplete reference in venom data: {}", effect);
            return this;
        }
        return new VenomDataComponent(new MobEffectInstance(effect), this.harmful());
    }

    public VenomDataComponent withBeneficial(@NotNull Holder<MobEffect> effect, int duration, int amplifier) {
        return withBeneficial(new MobEffectInstance(effect, duration, amplifier));
    }
    public VenomDataComponent withoutBeneficial() {
        return new VenomDataComponent(null, this.harmful());
    }

    public VenomDataComponent withHarmful(@Nullable MobEffectInstance effect) {
        if (effect != null && effect.getEffect().kind() != Holder.Kind.REFERENCE) {
            SullysMod.LOGGER.error("Attempted to set harmful effect to incomplete reference in venom data: {}", effect);
            return this;
        }else if(effect == null) return withoutHarmful();

        return new VenomDataComponent(beneficial(), new MobEffectInstance(effect));
    }
    public VenomDataComponent withHarmful(@NotNull Holder<MobEffect> effect, int duration, int amplifier) {
        return withHarmful(new MobEffectInstance(effect, duration, amplifier));
    }
    public VenomDataComponent withoutHarmful() {
        return new VenomDataComponent(this.beneficial(), null);
    }

    public void applyBeneficialToLivingEntity(@NotNull LivingEntity target, @Nullable Entity sourceEntity) {
        if (!(target.level() instanceof ServerLevel serverLevel)) return;
        if (this.beneficial() != null) applyToLivingEntity(new MobEffectInstance(this.beneficial()), serverLevel, target, sourceEntity);
    }

    public void applyHarmfulToLivingEntity(@NotNull LivingEntity target, @Nullable Entity sourceEntity) {
        if (!(target.level() instanceof ServerLevel serverLevel)) return;
        if (this.harmful() != null) applyToLivingEntity(new MobEffectInstance(this.harmful()), serverLevel, target, sourceEntity);
    }

    public void applyBeneficialToLivingEntity(int duration, int amplifier, @NotNull LivingEntity target, @Nullable Entity sourceEntity) {
        if (!(target.level() instanceof ServerLevel serverLevel)) return;
        if (this.beneficial() == null) return;
        applyToLivingEntity(copyInstanceWith(this.beneficial(), duration, amplifier), serverLevel, target, sourceEntity);
    }

    public void applyHarmfulToLivingEntity(int duration, int amplifier, @NotNull LivingEntity target, @Nullable Entity sourceEntity) {
        if (!(target.level() instanceof ServerLevel serverLevel)) return;
        if (this.harmful() == null) return;
        applyToLivingEntity(copyInstanceWith(this.harmful(), duration, amplifier), serverLevel, target, sourceEntity);
    }

    public void applyToLivingEntity(int duration, int amplifier, @NotNull LivingEntity target, @Nullable Entity sourceEntity) {
        applyBeneficialToLivingEntity(duration, amplifier, target, sourceEntity);
        applyHarmfulToLivingEntity(duration, amplifier, target, sourceEntity);
    }



    private void applyToLivingEntity(MobEffectInstance instance, ServerLevel level, @NotNull LivingEntity target, @Nullable Entity sourceEntity) {
        if (instance.getEffect().value().isInstantenous()) {
            instance.getEffect().value().applyInstantenousEffect(level,sourceEntity,sourceEntity,target,instance.getDuration(),instance.getAmplifier());
        }else{
            target.addEffect(instance);
        }
    }

    private static MobEffectInstance copyInstanceWith(MobEffectInstance effect, int duration, int amplifier) {
        return new MobEffectInstance(
                effect.getEffect(),
                duration,
                amplifier,
                effect.isAmbient(),
                effect.isVisible(),
                effect.showIcon()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof VenomDataComponent otherVenomData)) return false;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        int code = 31;
        if (this.beneficial != null) code *= this.beneficial.hashCode();
        code += 31;
        if (this.harmful != null) code *= 31 * this.harmful.hashCode();
        return code;
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        List<MobEffectInstance> effectList = new ArrayList<>();
        if (this.beneficial != null) { effectList.add(this.beneficial()); }
        if (this.harmful != null) { effectList.add(this.harmful()); }
        PotionContents.addPotionTooltip(effectList, consumer, 1.0F, tooltipContext.tickRate());
    }
}
