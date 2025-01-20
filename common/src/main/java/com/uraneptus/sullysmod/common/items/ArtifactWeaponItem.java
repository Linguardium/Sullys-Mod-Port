package com.uraneptus.sullysmod.common.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ArtifactWeaponItem extends Item {
    @Nullable
    private final Supplier<SoundEvent> customBreakSound;
//    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ArtifactWeaponItem(ToolMaterial toolMaterial, @Nullable Supplier<SoundEvent> customBreakSound, Properties properties) {
        super(toolMaterial.applySwordProperties(properties, 0, 0));
        this.customBreakSound = customBreakSound;
//        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", damage, AttributeModifier.Operation.ADDITION));
//        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", speed, AttributeModifier.Operation.ADDITION));
//        this.defaultModifiers = builder.build();
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (customBreakSound == null) {
            pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
            return true;
        }
        if (pAttacker instanceof ServerPlayer serverPlayer) {
            pStack.hurtAndBreak(1, serverPlayer.serverLevel(),serverPlayer, stack-> {
                serverPlayer.serverLevel().playSound(null, serverPlayer.getOnPos(), customBreakSound.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            });
        }
        return true;
    }

//    @NotNull
//    @Override
//    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
//        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
//    }
}
