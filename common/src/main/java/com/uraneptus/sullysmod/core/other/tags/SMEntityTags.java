package com.uraneptus.sullysmod.core.other.tags;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.core.other.SMTagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class SMEntityTags {
    //Mod Tags
    public static final TagKey<EntityType<?>> SCARES_TORTOISES = SMTagUtil.entityTypeTag(SullysMod.MOD_ID, "scares_tortoises");
    public static final TagKey<EntityType<?>> CANNOT_BOUNCE = SMTagUtil.entityTypeTag(SullysMod.MOD_ID, "cannot_bounce");
    public static final TagKey<EntityType<?>> CANNOT_BE_FLUNG = SMTagUtil.entityTypeTag(SullysMod.MOD_ID, "cannot_be_flung");
    public static final TagKey<EntityType<?>> ATTACKS_BABY_TORTOISES = SMTagUtil.entityTypeTag(SullysMod.MOD_ID, "attacks_baby_tortoises");
    public static final TagKey<EntityType<?>> IS_LIVING_INORGANIC = SMTagUtil.entityTypeTag(SullysMod.MOD_ID, "is_living_inorganic");
    public static final TagKey<EntityType<?>> PIRANHA_ALWAYS_ATTACKS = SMTagUtil.entityTypeTag(SullysMod.MOD_ID, "piranha_awlways_attacks");
    public static final TagKey<EntityType<?>> SPAWN_IN_AMBER = SMTagUtil.entityTypeTag(SullysMod.MOD_ID, "spawn_in_amber");
}
