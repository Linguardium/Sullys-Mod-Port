package com.uraneptus.sullysmod.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.uraneptus.sullysmod.common.components.RarityComponent.getRarityFormatting;

@Mixin(Gui.class)
public class CustomRarity$GuiMixin {
    @ModifyReceiver(method="renderSelectedItemName", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getHoverName()Lnet/minecraft/network/chat/Component;"))
    private ItemStack grabItemStack(ItemStack stack, @Share(value="hoverStack") LocalRef<ItemStack> hoverStack) {
        hoverStack.set(stack);
        return stack;
    }

    @ModifyExpressionValue(method = "renderSelectedItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent applyCustomRarityFormatting(MutableComponent original, @Share(value = "hoverStack") LocalRef<ItemStack> hoverStack) {
        ItemStack stack = hoverStack.get();
        if (stack == null || stack.isEmpty()) return original;
        Style customFormatting = getRarityFormatting(stack);
        if (customFormatting == null) return original;
        return original.withStyle(customFormatting);
    }

}
