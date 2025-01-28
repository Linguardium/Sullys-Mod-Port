package com.uraneptus.sullysmod.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.uraneptus.sullysmod.common.components.RarityComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class CustomRarity$ItemStackMixin {
    @WrapOperation(method = {"getStyledHoverName","getDisplayName"}, at= @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent getStyledHoverName(MutableComponent instance, ChatFormatting originalFormatting, Operation<MutableComponent> original) {
        Style rarityFormatting = RarityComponent.getRarityFormatting((ItemStack)(Object)this);
        if (rarityFormatting != null) return instance.withStyle(rarityFormatting);
        return original.call(instance, originalFormatting);
    }
}
