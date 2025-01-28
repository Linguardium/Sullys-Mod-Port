package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.levelgen.PetrifiedTreeGravelDecorator;
import com.uraneptus.sullysmod.mixins.TreeDecoratorTypeHelper;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.TREE_DECORATOR_TYPES;

public class SMTreeDecoratorTypes {

    public static final RegistrySupplier<TreeDecoratorType<PetrifiedTreeGravelDecorator>> GRAVEL_DECORATOR = TREE_DECORATOR_TYPES.register(location("petrified_gravel_decorator"), () -> TreeDecoratorTypeHelper.create(PetrifiedTreeGravelDecorator.CODEC));

    public static void init() { }
}
