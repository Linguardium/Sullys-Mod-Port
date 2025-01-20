package com.uraneptus.sullysmod.common.items;

import com.google.common.collect.Multimap;
import com.uraneptus.sullysmod.core.registry.SMItems;
import net.minecraft.core.dispenser.EquipmentDispenseItemBehavior;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class JadeShieldItem extends Item {

    public JadeShieldItem(Properties pProperties) {
        super(pProperties);
        DispenserBlock.registerBehavior(this, EquipmentDispenseItemBehavior.INSTANCE);
    }
    public static ItemAttributeModifiers createJadeShieldModifiers(float useSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, useSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND
                )
                .build();
    }
//    @Override
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            @Override
//            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
//                return new JadeShieldRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
//            }
//        });
//    }


    @Override
    public ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 72000;
    }


    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        pPlayer.startUsingItem(pHand);
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pToRepair.is(SMItems.JADE.get());
    }

    @NotNull
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slotType) {
        return slotType.getType() == EquipmentSlot.Type.HAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slotType);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }
}
