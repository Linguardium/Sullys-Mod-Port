package com.uraneptus.sullysmod.core.other;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

public class SMLocationUtil {
    public static ResourceLocation location(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }
    public static <T> ResourceKey<T> key(ResourceKey<Registry<T>> registry, String id) {
        return ResourceKey.create(registry,location(id));
    }
}
