package com.uraneptus.sullysmod.core.other.tags;

import com.uraneptus.sullysmod.core.other.SMTagUtil;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SMItemTags {
    //Our Tags
    public static final TagKey<Item> TORTOISE_FOOD = SMTagUtil.itemTag("tortoise_food");
    // TODO: check for common meat itemtag
    public static final TagKey<Item> CARNIVORE_CONSUMABLES = SMTagUtil.itemTag("carnivore_consumables");
    public static final TagKey<Item> PETRIFIED_LOGS = SMTagUtil.itemTag("petrified_logs");
    public static final TagKey<Item> ARTIFACTS = SMTagUtil.itemTag("artifacts");
    public static final TagKey<Item> ANCIENT_SKULLS = SMTagUtil.itemTag("artifacts/ancient_skulls");
    public static final TagKey<Item> UNREPAIRABLE = SMTagUtil.itemTag("empty_tag");
    public static final TagKey<Item> WORKSTATIONS = SMTagUtil.itemTag("empty_tag");
        // SMItemTags.CRAFTING_TABLES) || itemInHand.is(SMItemTags.JUKEBOXES)
        // TODO: datagen workstation tag

    //Forge Tags
    // TODO: common tags
    public static final TagKey<Item> AXE_ITEMS = ItemTags.AXES;
    public static final TagKey<Item> RAW_FISHES = SMTagUtil.itemTag("c", "foods/raw_fish");
    public static final TagKey<Item> RAW_LANTERNFISH = SMTagUtil.subGroup(RAW_FISHES,"lanternfish");
    public static final TagKey<Item> RAW_PIRANHA = SMTagUtil.subGroup(RAW_FISHES,"piranha");
    public static final TagKey<Item> COOKED_FISHES = SMTagUtil.itemTag("c", "foods/cooked_fish");
    public static final TagKey<Item> COOKED_LANTERNFISH = SMTagUtil.subGroup(COOKED_FISHES,"lanternfish");
    public static final TagKey<Item> COOKED_PIRANHA = SMTagUtil.subGroup(COOKED_FISHES,"piranha");
    public static final TagKey<Item> JADE_ORES = SMTagUtil.itemTag("c", "ores/jade");
    public static final TagKey<Item> JADE_GEM = SMTagUtil.itemTag("c", "gems/jade");
    public static final TagKey<Item> RAW_MATERIALS_JADE = SMTagUtil.itemTag("c", "raw_materials/jade");
    public static final TagKey<Item> CRAFTING_TABLES = SMTagUtil.itemTag("c", "crafting_tables");
    public static final TagKey<Item> JUKEBOXES = SMTagUtil.itemTag("c", "jukeboxes");
    public static final TagKey<Item> SHEARS = SMTagUtil.itemTag("c", "tools/shear");
    public static final TagKey<Item> HONEYCOMBS = SMTagUtil.itemTag("c", "honeycombs");

}
