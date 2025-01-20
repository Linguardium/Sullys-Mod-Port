package com.uraneptus.sullysmod.core.other;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class SMTagUtil {
    public static TagKey<Block> blockTag(ResourceLocation id) {
        return TagKey.create(Registries.BLOCK, id);
    }
    public static TagKey<Block> blockTag(String id) {
        return TagKey.create(Registries.BLOCK, location(id));
    }
    public static TagKey<Block> blockTag(String namespace, String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }
}
