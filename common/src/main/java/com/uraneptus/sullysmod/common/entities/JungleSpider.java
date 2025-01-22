package com.uraneptus.sullysmod.common.entities;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.common.entities.components.JungleSpiderEffectData;
import com.uraneptus.sullysmod.common.entities.group_spawn_data.JungleSpiderSpawnGroupData;
import com.uraneptus.sullysmod.core.other.tags.SMMobEffectTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JungleSpider extends Spider {

    @NotNull JungleSpiderEffectData effectData = JungleSpiderEffectData.EMPTY;
    public JungleSpider(EntityType<? extends JungleSpider> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData) {
        if (!(spawnGroupData instanceof JungleSpiderSpawnGroupData)) {
            spawnGroupData = JungleSpiderSpawnGroupData.generate(serverLevelAccessor.getRandom());
        }
        if (spawnGroupData instanceof JungleSpiderSpawnGroupData jungleSpiderSpawnGroupData) {
            this.effectData = jungleSpiderSpawnGroupData.effectData;
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, entitySpawnReason, spawnGroupData);
    }

    // TODO: SpawnRestrictions
    public static boolean checkJungleSpiderSpawnRules(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, EntitySpawnReason entitySpawnReason, BlockPos pPos, RandomSource pRandom) {
        return Monster.checkMonsterSpawnRules(pType, pLevel, entitySpawnReason, pPos, pRandom);
    }

    public Optional<Holder<MobEffect>> getBeneficialVenomEffect() {
        return Optional.ofNullable(this.effectData.beneficial());
    }

    public Optional<Holder<MobEffect>> getHarmfulVenomEffect() {
        return Optional.ofNullable(this.effectData.harmful());
    }

    public void setBeneficialVenomEffect(@Nullable Holder<MobEffect> mobEffect) {
        this.effectData = effectData.withBeneficial(mobEffect);
    }

    public void setHarmfulVenomEffect(@Nullable Holder<MobEffect> mobEffect) {
        this.effectData = effectData.withHarmful(mobEffect);
    }

    private static boolean isEffectExtended(Holder<MobEffect> mobEffect) {
        return mobEffect.is(SMMobEffectTags.EXTENDED_VENOM_EFFECTS);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 18.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F);
    }

    private static MobEffectInstance getOnHitEffect(ServerLevel level, BlockPos pos, Holder<MobEffect> mobEffect) {
        float difficultyMultiplier = level.getCurrentDifficultyAt(pos).getEffectiveDifficulty() * 5f;
        int seconds = Mth.floor(difficultyMultiplier * 20);
        if (isEffectExtended(mobEffect)) seconds += 5;
        if (seconds < 1) seconds = 1;

        // hide effect icon on hard
        // TODO: config for hiding effect?
        return new MobEffectInstance(mobEffect, seconds, 0, false, false, level.getDifficulty()!=Difficulty.HARD);
    }
    @Override
    public boolean doHurtTarget(ServerLevel serverLevel, Entity entity) {
        if (!super.doHurtTarget(serverLevel, entity))  return false;
        if (!(entity instanceof LivingEntity livingEntity)) return true;
        if (serverLevel.getDifficulty() == Difficulty.PEACEFUL) return true;
        getBeneficialVenomEffect()
                .map(effect->getOnHitEffect(serverLevel, livingEntity.getOnPos(), effect))
                .ifPresent(livingEntity::addEffect);
        getHarmfulVenomEffect()
                .map(effect->getOnHitEffect(serverLevel, livingEntity.getOnPos(), effect))
                .ifPresent(livingEntity::addEffect);
        return true;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("EffectData", JungleSpiderEffectData.CODEC.codec().encodeStart(NbtOps.INSTANCE, this.effectData).getOrThrow());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        JungleSpiderEffectData.CODEC.codec().parse(NbtOps.INSTANCE, compoundTag.getCompound("EffectData")).ifError(e->SullysMod.LOGGER.error(e.message())).ifSuccess(data->this.effectData = data);
    }
// TODO: entity dimensions
//    @Override
//    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
//        return 0.45F;
//    }
}
