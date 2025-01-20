package com.uraneptus.sullysmod.core.other;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class SMTagUtil {
    public static TagKey<Block> blockTag(String id) {
        return TagKey.create(Registries.BLOCK, location(id));
    }
    public static TagKey<Block> blockTag(String namespace, String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static TagKey<Item> itemTag(String id) {
        return TagKey.create(Registries.ITEM, location(id));
    }
    public static TagKey<Item> itemTag(String namespace, String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static TagKey<EntityType<?>> entityTypeTag(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, location(id));
    }
    public static TagKey<EntityType<?>> entityTypeTag(String namespace, String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static TagKey<Biome> biomeTag(String id) {
        return TagKey.create(Registries.BIOME, location(id));
    }
    public static TagKey<Biome> biomeTag(String namespace, String path) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static TagKey<MobEffect> mobEffectTag(String id) {
        return TagKey.create(Registries.MOB_EFFECT, location(id));
    }
    public static TagKey<MobEffect> mobEffectTag(String namespace, String path) {
        return TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static <T> TagKey<T> subGroup(TagKey<T> tag, String subGroup) {
        return TagKey.create(tag.registry(), tag.location().withSuffix("/"+subGroup));
    }

}
