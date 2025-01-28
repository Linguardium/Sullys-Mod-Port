package com.uraneptus.sullysmod.client.renderer.entities;

import net.minecraft.client.model.geom.ModelLayerLocation;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class SMModelLayers {
    public static final ModelLayerLocation MINERS_HELMET = registerMain("miners_helmet");

    public static final ModelLayerLocation BOULDER_ZOMBIE = registerMain("bouldering_zombie");
    public static final ModelLayerLocation BOULDER_ZOMBIE_INNER_ARMOR =registerInnerArmor("bouldering_zombie");
    public static final ModelLayerLocation BOULDER_ZOMBIE_OUTER_ARMOR = registerOuterArmor("bouldering_zombie");
    public static final ModelLayerLocation BOULDER_ZOMBIE_BABY = asBaby(BOULDER_ZOMBIE);
    public static final ModelLayerLocation BOULDER_ZOMBIE_INNER_ARMOR_BABY = asBaby(BOULDER_ZOMBIE_INNER_ARMOR);
    public static final ModelLayerLocation BOULDER_ZOMBIE_OUTER_ARMOR_BABY = asBaby(BOULDER_ZOMBIE_OUTER_ARMOR);
    public static final ModelLayerLocation JUNGLE_SPIDER = registerMain("jungle_spider");
    public static final ModelLayerLocation LANTERN_FISH = registerMain("lanternfish");
    public static final ModelLayerLocation PIRANHA = registerMain("piranha");
    public static final ModelLayerLocation TORTOISE = registerMain("tortoise");
    public static final ModelLayerLocation TORTOISE_SHELL = registerMain("tortoise_shell");

    public static void init() {

    }
    private static ModelLayerLocation register(String name, String layer) {
        return new ModelLayerLocation(location(name), layer);
    }
    private static ModelLayerLocation registerMain(String name) {
        return new ModelLayerLocation(location(name), "main");
    }
    private static ModelLayerLocation registerInnerArmor(String name) {
        return new ModelLayerLocation(location(name), "inner_armor");
    }
    private static ModelLayerLocation registerOuterArmor(String name) {
        return new ModelLayerLocation(location(name), "outer_armor");
    }
    private static ModelLayerLocation asBaby(ModelLayerLocation adult) {
        return new ModelLayerLocation(adult.model().withSuffix("_baby"), adult.layer());
    }

}
