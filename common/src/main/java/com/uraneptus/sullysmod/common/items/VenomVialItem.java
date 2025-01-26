package com.uraneptus.sullysmod.common.items;

import com.uraneptus.sullysmod.common.components.VenomDataComponent;
import com.uraneptus.sullysmod.core.registry.SMItemDataComponentTypes;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VenomVialItem extends Item {
    public VenomVialItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.set(SMItemDataComponentTypes.VENOM_DATA_COMPONENT.get(),
                VenomDataComponent.EMPTY
                        .withBeneficial(MobEffects.MOVEMENT_SPEED, 200, 0)
                        .withHarmful(MobEffects.POISON, 200, 0)
        );
        return stack;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    private void applyEffectsToLivingEntity(ItemStack stack, LivingEntity target, @Nullable Entity sourceEntity) {
        VenomDataComponent venom = stack.getOrDefault(SMItemDataComponentTypes.VENOM_DATA_COMPONENT.get(), VenomDataComponent.EMPTY);
        venom.applyToLivingEntity(200, 0, target,sourceEntity);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        // change in behavior. original passed null if it wasnt a player causing the effect
        applyEffectsToLivingEntity(stack, livingEntity, livingEntity);

        stack.consume(1, player);

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.isCreative()) {
                level.playSound(null, livingEntity.getOnPos(), SMSounds.VIAL_SHATTERS.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }


        livingEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
        return 24;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        itemStack.getOrDefault(SMItemDataComponentTypes.VENOM_DATA_COMPONENT.get(), VenomDataComponent.EMPTY).addToTooltip(tooltipContext,list::add,tooltipFlag);
    }

//    TODO: Venom Vial Item Tint Source

//    public static int getEffectColours(ItemStack stack, int tintIndex) {
//        Color effectColor;
//        if (tintIndex == 1) {
//            effectColor = new Color(0xFF000000 | getBeneficialEffect(stack).getColor());
//        }
//        else {
//            effectColor = new Color(0xFF000000 | getHarmfulEffect(stack).getColor()).brighter();
//        }
//        return effectColor.getRGB();
//    }
}
