package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.entities.*;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.ENTITY_TYPES;


public class SMEntityTypes {

    public static final RegistrySupplier<EntityType<Lanternfish>> LANTERNFISH = register("lanternfish", Lanternfish::new, MobCategory.UNDERGROUND_WATER_CREATURE, 0.5F, 0.3F);
    public static final RegistrySupplier<EntityType<Tortoise>> TORTOISE = register("tortoise", Tortoise::new, MobCategory.CREATURE, 1.1F, 1.1F);
    public static final RegistrySupplier<EntityType<TortoiseShell>> TORTOISE_SHELL = register("tortoise_shell", TortoiseShell::new, MobCategory.MISC, 1.0F, 0.9F);
    public static final RegistrySupplier<EntityType<BoulderingZombie>> BOULDERING_ZOMBIE = register("bouldering_zombie", BoulderingZombie::new, MobCategory.MONSTER, 0.6F, 1.95F);
    public static final RegistrySupplier<EntityType<JungleSpider>> JUNGLE_SPIDER = register("jungle_spider", JungleSpider::new, MobCategory.MONSTER, 0.85F, 0.9F);
    public static final RegistrySupplier<EntityType<Piranha>> PIRANHA = register("piranha", Piranha::new, MobCategory.WATER_AMBIENT, 0.65F, 0.4F);
    public static final RegistrySupplier<EntityType<ThrownThrowingKnife>> THROWN_THROWING_KNIFE = register("thrown_throwing_knife", ThrownThrowingKnife::new, MobCategory.MISC, 0.5F, 0.6F);

    private static <M extends Entity> RegistrySupplier<EntityType<M>> register(String name, EntityType.EntityFactory<M> factory, MobCategory category, float width, float height) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, location(name));
        return ENTITY_TYPES.register(key.location(), ()-> EntityType.Builder.<M>of(factory, category)
                .sized(width, height)
                .build(key));
    }

    public static void init() { }
}