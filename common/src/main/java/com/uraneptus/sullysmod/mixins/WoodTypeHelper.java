package com.uraneptus.sullysmod.mixins;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WoodType.class)
public interface WoodTypeHelper {
    @Invoker("register")
    static WoodType registerWoodType(WoodType woodType) {
        throw new UnsupportedOperationException("Implemented by mixin");
    }
}
