package com.uraneptus.sullysmod.mixins;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface TreeDecoratorTypeHelper {
    @Invoker("<init>")
    static <P extends TreeDecorator> TreeDecoratorType<P> create(MapCodec<P> mapCodec) {
        throw new UnsupportedOperationException("Implemented by Mixin");
    }

}
