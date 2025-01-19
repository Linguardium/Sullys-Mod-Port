package com.uraneptus.sullysmod.mixins;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockSetType.class)
public interface BlockSetTypeHelper {
    @Invoker("register")
    static BlockSetType registerType(BlockSetType type) {
        throw new UnsupportedOperationException("Implemented by mixin");
    }
}
