package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.items.*;
import com.uraneptus.sullysmod.core.other.SMArmorMaterials;
import com.uraneptus.sullysmod.core.other.SMProperties;
import com.uraneptus.sullysmod.core.other.SMTextDefinitions;
import com.uraneptus.sullysmod.core.other.SMTextUtil;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.other.SMProperties.jadeProperties;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.ITEMS;

public class SMItems {
    // TODO: Move autotranslate to datagen classes and stub out if not running datagen
    public static List<RegistrySupplier<? extends Item>> AUTO_TRANSLATE = new ArrayList<>();

    //We have this here so KubeJS can access it!
    // TODO: ANCIENT Rarity
    //public static final Rarity ANCIENT = Rarity.create("sullysmod:ancient", style -> style.withColor(15107584));

    //Basic Items
    public static final RegistrySupplier<Item> ROUGH_JADE = createItem("rough_jade");
    public static final RegistrySupplier<Item> JADE = createItem("jade");
    public static final RegistrySupplier<Item> MUSIC_DISC_SCOUR = createItem("music_disc_scour", () -> new SMRecordItem(12, SMSounds.MUSIC_DISC_SCOUR, SMProperties.Items.MUSIC_DISCS, 4980), true);
    public static final RegistrySupplier<Item> MUSIC_DISC_SUNKEN_PAST = createItem("music_disc_sunken_past", () -> new SMRecordItem(12, SMSounds.MUSIC_DISC_SUNKEN_PAST, SMProperties.Items.MUSIC_DISCS, 2700), true); //Doesn't have a feature category yet
    public static final RegistrySupplier<Item> TORTOISE_SCUTE = createItem("tortoise_scute");
    public static final RegistrySupplier<Item> TORTOISE_SHELL = createItem("tortoise_shell", () -> new TortoiseShellItem(SMProperties.stacksOnce()));
    public static final RegistrySupplier<Item> JADE_UPGRADE_SMITHING_TEMPLATE = createItem("jade_upgrade_smithing_template", JadeSmithingTemplateItem::new, true);
    public static final RegistrySupplier<Item> GLASS_VIAL = createItem("glass_vial", () -> new VialItem(new Item.Properties()));
    public static final RegistrySupplier<Item> VENOM_VIAL = createItem("venom_vial", () -> new VenomVialItem(new Item.Properties().stacksTo(16)), true);
    public static final RegistrySupplier<Item> JADE_HORSE_ARMOR = createItem("jade_horse_armor", () -> new HorseArmorItem(9, "jade", jadeProperties().stacksTo(1)));
    public static final RegistrySupplier<Item> PIRANHA_TOOTH = createItem("piranha_tooth");

    //Tools
    public static final RegistrySupplier<Item> JADE_SHIELD = createItem("jade_shield", () -> new JadeShieldItem(SMProperties.Items.JADE_SHIELD));
    public static final RegistrySupplier<Item> THROWING_KNIFE = createItem("throwing_knife", () -> new ThrowingKnifeItem(SMProperties.Items.sixteenStack()));

    //Food
    public static final RegistrySupplier<Item> LANTERNFISH = createItem("lanternfish", new Item.Properties().food(SMProperties.Foods.LANTERNFISH_FOOD), true);
    public static final RegistrySupplier<Item> COOKED_LANTERNFISH = createItem("cooked_lanternfish", new Item.Properties().food(SMProperties.Foods.COOKED_LANTERNFISH_FOOD));
    public static final RegistrySupplier<Item> PIRANHA = createItem("piranha", new Item.Properties().food(SMProperties.Foods.PIRANHA_FOOD), true);
    public static final RegistrySupplier<Item> COOKED_PIRANHA = createItem("cooked_piranha", new Item.Properties().food(SMProperties.Foods.COOKED_PIRANHA_FOOD));

    //Buckets & Spawn Eggs
    public static final RegistrySupplier<Item> MOLTEN_AMBER_BUCKET = createItem("molten_amber_bucket", () -> new ArchitecturyBucketItem(SMFluids.SOURCE_MOLTEN_AMBER, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> LANTERNFISH_BUCKET = createMobBucketItem("lanternfish_bucket", SMEntityTypes.LANTERNFISH);
    public static final RegistrySupplier<Item> LANTERNFISH_SPAWN_EGG = createSpawnEggItem("lanternfish", SMEntityTypes.LANTERNFISH, 0xFCE3D3, 9306085);
    public static final RegistrySupplier<Item> TORTOISE_SPAWN_EGG = createSpawnEggItem("tortoise", SMEntityTypes.TORTOISE, 15198183, 10844478);
    public static final RegistrySupplier<Item> BOULDERING_ZOMBIE_SPAWN_EGG = createSpawnEggItem("bouldering_zombie", SMEntityTypes.BOULDERING_ZOMBIE, 8142370, 4608338);
    public static final RegistrySupplier<Item> JUNGLE_SPIDER_SPAWN_EGG = createSpawnEggItem("jungle_spider", SMEntityTypes.JUNGLE_SPIDER, 5597514, 11013646);
    public static final RegistrySupplier<Item> PIRANHA_BUCKET = createMobBucketItem("piranha_bucket", SMEntityTypes.PIRANHA);
    public static final RegistrySupplier<Item> PIRANHA_SPAWN_EGG = createSpawnEggItem("piranha", SMEntityTypes.PIRANHA, 15561472, 4240022);

    //Artifacts
    public static Map<RegistrySupplier<? extends Item>, Component> ARTIFACT_DESC_MAP = new HashMap<>();
    public static Map<Supplier<? extends Item>, Integer> TRADES = new HashMap<>();

    public static final RegistrySupplier<Item> BROKEN_VASE = registerArtifact("broken_vase", "A large piece of the side is missing", 10);
    public static final RegistrySupplier<Item> PRIMITIVE_KNIFE = registerArtifact("primitive_knife", "A small knife made from obsidian", () -> new ArtifactWeaponItem(SMToolMaterials.PRIMITIVE_KNIFE, null, SMProperties.Items.artifacts().stacksTo(1)), 15);
    public static final RegistrySupplier<Item> MINERS_HELMET = registerArtifact("miners_helmet", "Looks like it’s previous owner couldn’t get the candle lit anymore",
            () -> new MinersHelmetItem(SMProperties.Items.artifacts().stacksTo(1)), 15);
    public static final RegistrySupplier<Item> SMALL_DENTED_HELMET = registerArtifact("small_dented_helmet", "A small rusty helmet. Barely fits",
            () -> new ArtifactHelmetItem(SMArmorMaterials.SMALL_DENTED_HELMET, SMProperties.Items.artifacts().stacksTo(1)), 22);
    public static final RegistrySupplier<Item> LOST_CROWN = registerArtifact("lost_crown", "Once belonged to the king of a now fallen kingdom",
            () -> new ArtifactHelmetItem(SMArmorMaterials.LOST_CROWN, SMProperties.Items.artifacts().stacksTo(1)), 30);
    public static final RegistrySupplier<Item> JADE_AMULET = registerArtifact("jade_amulet", "A creature is carefully sculpted from the stone", 20);
    public static final RegistrySupplier<Item> PRIMITIVE_RING = registerArtifact("primitive_ring", "A roughly made metal ring", 10);
    public static final RegistrySupplier<Item> RUSTY_TOOLS = registerArtifact("rusty_tools", "Maybe their owners are still out there", 9);
    public static final RegistrySupplier<Item> BROKEN_BOWL = registerArtifact("broken_bowl", "A large crack runs down the edge", 9);
    public static final RegistrySupplier<Item> COPPER_COG = registerArtifact("copper_cog", "Said to have been part of living creatures", 23);
    public static final RegistrySupplier<Item> PETRIFIED_COOKIE = registerArtifact("petrified_cookie", "Petrified food is still food, just extra crisp", () -> new Item(SMProperties.Items.artifacts().food(SMProperties.Foods.PETRIFIED_COOKIE)), 12);
    public static final RegistrySupplier<Item> ARROWHEAD = registerArtifact("arrowhead", "The tip of an ancient arrow", 5);
    public static final RegistrySupplier<Item> DEATH_WHISTLE = registerArtifact("death_whistle", "Screeches horrible noises when blown into", DeathWhistleItem::new, 20);
    public static final RegistrySupplier<Item> OMINOUS_TABLET = registerArtifact("ominous_tablet", "A dark figure is carved into the stone", 25);
    public static final RegistrySupplier<Item> MOON_TABLET = registerArtifact("moon_tablet", "Has a carved image of the moon", 27);
    public static final RegistrySupplier<Item> STONE_IDOL = registerArtifact("stone_idol", "Almost looks alive", 20);
    public static final RegistrySupplier<Item> RED_CAP = registerArtifact("red_cap", "A tiny red cap. It’s too small to wear and the fabric feels strange", 20);
    public static final RegistrySupplier<Item> DRIED_CYAN_FLOWER = registerArtifact("dried_cyan_flower", "A delicate cyan flower that feels strangely familiar", 21);
    public static final RegistrySupplier<Item> DRIED_RED_FLOWER = registerArtifact("dried_red_flower", "A delicate red flower that feels like home", 22);
    public static final RegistrySupplier<Item> METALLIC_SKULL = registerArtifact("metallic_skull", "The a metallic skull attached to broken off bars", 25);
    public static final RegistrySupplier<Item> LOST_BAG = registerArtifact("lost_bag", "A small lightweight bag sloppily sewn together", 14);
    public static final RegistrySupplier<Item> MYSTERIOUS_PLATE = registerArtifact("mysterious_plate", "Made from an unknown material", 35);
    public static final RegistrySupplier<Item> FAMILIAR_CUBE = registerArtifact("familiar_cube", "Hot to the touch and has strange growths on it", 30);
    public static final RegistrySupplier<Item> AMBER_ENCASED_BUG = registerArtifact("amber_encased_bug", "A small bug that was covered by tree sap ages ago", 20);
    public static final RegistrySupplier<Item> FOSSILISED_SHELLS = registerArtifact("fossilised_shells", "Shells from a sea creature that lived long ago", 27);
    public static final RegistrySupplier<Item> FOSSILISED_BONE = registerArtifact("fossilised_bone", "A large bone of an extinct creature", 27);
    public static final RegistrySupplier<Item> FOSSILISED_FOOTSTEP = registerArtifact("fossilised_footstep", "An ancient footprint that never faded", 25);
    public static final RegistrySupplier<Item> FOSSILISED_FISH = registerArtifact("fossilised_fish", "The bones of a small fish from times past", 27);
    public static final RegistrySupplier<Item> TORN_MANUSCRIPT = registerArtifact("torn_manuscript", "Part of a manuscript with an unknown language", 18);
    public static final RegistrySupplier<Item> LOST_JOURNAL = registerArtifact("lost_journal", "Waterlogged and left unreadable, it has a few pages ripped out", 17);
    public static final RegistrySupplier<Item> LOST_SKETCHBOOK = registerArtifact("lost_sketchbook", "A small book with scratchy drawings of an unknown large mouthed biped", 24);
    public static final RegistrySupplier<Item> LOST_RECIPE_BOOK = registerArtifact("lost_recipe_book", "Mostly ruined and unreadable, but still has some recipes inside.", 12);
    public static final RegistrySupplier<Item> GOLDEN_BELT_BUCKLE = registerArtifact("golden_belt_buckle", "Has a peculiar shape and it’s leather feels strange", 26);
    public static final RegistrySupplier<Item> DEEPSLATE_VASE = registerArtifact("deepslate_vase", "Who would’ve needed a vase made from deepslate?", 25);
    public static final RegistrySupplier<Item> SMALL_GEODE = registerArtifact("small_geode", "Kind of cute", 23);
    public static final RegistrySupplier<Item> TORN_CLOTH = registerArtifact("torn_cloth", "A dirty torn off piece of clothing", 6);
    public static final RegistrySupplier<Item> GOLDEN_GOBLET = registerArtifact("golden_goblet", "An old but beautiful chalice made by a skilled goldsmith", 29);
    public static final RegistrySupplier<Item> EMERALD_EARRING = registerArtifact("emerald_earring", "Besides the beautiful emerald, it looks sloppily put together", 17);
    public static final RegistrySupplier<Item> BROKEN_BOTTLE = registerArtifact("broken_bottle", "The top half of a bottle", () -> new ArtifactWeaponItem(SMToolMaterials.GLASS, SMSounds.BROKEN_BOTTLE_SHATTERS, SMProperties.Items.artifacts().stacksTo(1)), 5);
    public static final RegistrySupplier<Item> FROG_IDOL = registerArtifact("frog_idol", "Everybody likes frogs", 29);

    private static RegistrySupplier<Item> registerArtifact(String name, String description, int price) {
        return registerArtifact(name, description, () -> new Item(SMProperties.Items.artifacts()), price);
    }

    private static <I extends Item> RegistrySupplier<I> registerArtifact(String name, String description, Supplier<I> item, int price) {
        RegistrySupplier<I> object = createItem(name, item, true);
        ARTIFACT_DESC_MAP.put(object, SMTextUtil.addSMTranslatable("artifact." + name + ".desc", description).withStyle(SMTextDefinitions.ARTIFACT_DESC_STYLE));
        TRADES.put(object, price);
        return object;
    }

    private static RegistrySupplier<Item> createSpawnEggItem(String name, RegistrySupplier<EntityType<? extends Mob>> supplier, int primaryColor, int secondaryColor) {
        return createItem(name + "_spawn_egg", () -> new ArchitecturySpawnEggItem(supplier, new Item.Properties()));
    }

    private static RegistrySupplier<Item> createMobBucketItem(String name, Supplier<EntityType<? extends WaterAnimal>> entityType) {
        return createItem(name, () -> new MobBucketItem(entityType.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, SMProperties.Items.singleStack()), true);
    }

    private static RegistrySupplier<Item> createItem(String name, boolean customTranslation) {
        return createItem(name, new Item.Properties(), customTranslation);
    }

    private static RegistrySupplier<Item> createItem(String name) {
        return createItem(name, new Item.Properties());
    }

    private static RegistrySupplier<Item> createItem(String name, Item.Properties properties, boolean customTranslation) {
        return createItem(name, () -> new Item(properties), customTranslation);
    }

    private static RegistrySupplier<Item> createItem(String name, Item.Properties properties) {
        return createItem(name, () -> new Item(properties));
    }

    private static <I extends Item> RegistrySupplier<I> createItem(String name, Supplier<I> supplier, boolean customTranslation) {
        RegistrySupplier<I> item = createItem(name, supplier);
        if (customTranslation) AUTO_TRANSLATE.remove(item);
        return item;
    }

    public static <I extends Item> RegistrySupplier<I> createItem(String name, Supplier<I> supplier) {
        RegistrySupplier<I> item = ITEMS.register(location(name), supplier);
        AUTO_TRANSLATE.add(item);
        return item;
    }
}
