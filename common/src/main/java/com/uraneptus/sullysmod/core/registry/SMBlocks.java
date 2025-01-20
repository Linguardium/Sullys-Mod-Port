package com.uraneptus.sullysmod.core.registry;

import com.mojang.datafixers.util.Pair;
import com.uraneptus.sullysmod.common.blocks.*;
import com.uraneptus.sullysmod.common.blocks.utilities.SMDirectionalBlock;
import com.uraneptus.sullysmod.core.other.SMProperties;
import com.uraneptus.sullysmod.core.other.SMTextDefinitions;
import com.uraneptus.sullysmod.core.other.SMTextUtil;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.other.SMLocationUtil.optionalBlockLoot;
import static com.uraneptus.sullysmod.core.other.SMProperties.Blocks.FLOWER_POT;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.BLOCKS;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.ITEMS;


public class SMBlocks {
    public static List<RegistrySupplier<? extends Block>> AUTO_TRANSLATE = new ArrayList<>();

    //Jade
    public static final RegistrySupplier<Block> JADE_ORE = createBlock("jade_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), SMProperties.Blocks.JADE_ORE));
    public static final RegistrySupplier<Block> DEEPSLATE_JADE_ORE = createBlock("deepslate_jade_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), SMProperties.Blocks.DEEPSLATE_JADE_ORE));
    public static final RegistrySupplier<Block> ROUGH_JADE_BLOCK = createBlockNoLang("rough_jade_block", () -> new Block(SMProperties.Blocks.ROUGH_JADE_BLOCKS));
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICKS = createBlock("rough_jade_bricks", () -> new Block(SMProperties.Blocks.ROUGH_JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_BLOCK = createBlockNoLang("jade_block", () -> new Block(SMProperties.Blocks.JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_BRICKS = createBlock("jade_bricks", () -> new Block(SMProperties.Blocks.JADE_BLOCKS));
    public static final RegistrySupplier<Block> CHISELED_JADE = createBlock("chiseled_jade", () -> new Block(SMProperties.Blocks.JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_TOTEM = createBlock("jade_totem", () -> new SMDirectionalBlock(SMProperties.Blocks.JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_FLINGER_TOTEM = createBlock("jade_flinger_totem", () -> new FlingerTotem(SMProperties.Blocks.JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_PILLAR = createBlock("jade_pillar", () -> new RotatedPillarBlock(SMProperties.Blocks.JADE_BLOCKS));

    //Jade Stairs
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICK_STAIRS = createBlock("rough_jade_brick_stairs", () -> new StairBlock(ROUGH_JADE_BRICKS.get().defaultBlockState(), SMProperties.Blocks.ROUGH_JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_BRICK_STAIRS = createBlock("jade_brick_stairs", () -> new StairBlock(JADE_BRICKS.get().defaultBlockState(), SMProperties.Blocks.JADE_BLOCKS));

    //Jade Slabs
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICK_SLAB = createBlock("rough_jade_brick_slab", () -> new SlabBlock(SMProperties.Blocks.ROUGH_JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_BRICK_SLAB = createBlock("jade_brick_slab", () -> new SlabBlock(SMProperties.Blocks.JADE_BLOCKS));

    //Jade Walls
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICK_WALL = createBlock("rough_jade_brick_wall", () -> new WallBlock(SMProperties.Blocks.ROUGH_JADE_BLOCKS));
    public static final RegistrySupplier<Block> JADE_BRICK_WALL = createBlock("jade_brick_wall", () -> new WallBlock(SMProperties.Blocks.JADE_BLOCKS));

    //Copper Buttons
    public static final RegistrySupplier<Block> COPPER_BUTTON = createBlock("copper_button", () -> new WeatheringCopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 10, WeatheringCopper.WeatherState.UNAFFECTED));
    public static final RegistrySupplier<Block> EXPOSED_COPPER_BUTTON = createBlock("exposed_copper_button", () -> new WeatheringCopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 20, WeatheringCopper.WeatherState.EXPOSED));
    public static final RegistrySupplier<Block> WEATHERED_COPPER_BUTTON = createBlock("weathered_copper_button", () -> new WeatheringCopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 30, WeatheringCopper.WeatherState.WEATHERED));
    public static final RegistrySupplier<Block> OXIDIZED_COPPER_BUTTON = createBlock("oxidized_copper_button", () -> new WeatheringCopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 40, WeatheringCopper.WeatherState.OXIDIZED));
    public static final RegistrySupplier<Block> WAXED_COPPER_BUTTON = createBlock("waxed_copper_button", () -> new CopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 10));
    public static final RegistrySupplier<Block> WAXED_EXPOSED_COPPER_BUTTON = createBlock("waxed_exposed_copper_button", () -> new CopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 20));
    public static final RegistrySupplier<Block> WAXED_WEATHERED_COPPER_BUTTON = createBlock("waxed_weathered_copper_button", () -> new CopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 30));
    public static final RegistrySupplier<Block> WAXED_OXIDIZED_COPPER_BUTTON = createBlock("waxed_oxidized_copper_button", () -> new CopperButtonBlock(SMProperties.Blocks.COPPER_BUTTONS, BlockSetType.COPPER, 40));

    //Amber
    public static final RegistrySupplier<Block> AMBER = createBlock("amber", () -> new AmberBlock(SMProperties.Blocks.AMBER));
    public static final RegistrySupplier<Block> AMBER_BRICKS = createBlock("amber_bricks", () -> new SolidAmberBlock(SMProperties.Blocks.AMBER_BUILDING_BLOCKS));
    public static final RegistrySupplier<Block> AMBER_BRICK_STAIRS = createBlock("amber_brick_stairs", () -> new AmberStairBlock(() -> AMBER_BRICKS.get().defaultBlockState(), SMProperties.Blocks.AMBER_BUILDING_BLOCKS));
    public static final RegistrySupplier<Block> AMBER_BRICK_SLAB = createBlock("amber_brick_slab", () -> new AmberSlabBlock(SMProperties.Blocks.AMBER_BUILDING_BLOCKS));
    public static final RegistrySupplier<Block> AMBER_BRICK_WALL = createBlock("amber_brick_wall", () -> new AmberWallBlock(SMProperties.Blocks.AMBER_BUILDING_BLOCKS));
    public static final RegistrySupplier<Block> ROUGH_AMBER = createBlock("rough_amber", () -> new SolidAmberBlock(SMProperties.Blocks.AMBER_BUILDING_BLOCKS));
    public static final RegistrySupplier<Block> CHISELED_AMBER = createBlock("chiseled_amber", () -> new SolidAmberBlock(SMProperties.Blocks.AMBER_BUILDING_BLOCKS));
    public static final RegistrySupplier<Block> AMBER_PILLAR = createBlock("amber_pillar", () -> new AmberRotatedPillarBlock(SMProperties.Blocks.AMBER_BUILDING_BLOCKS));
    public static final RegistrySupplier<LiquidBlock> MOLTEN_AMBER_BLOCK = createBlockNoItem("molten_amber_block", () -> new ArchitecturyLiquidBlock(SMFluids.SOURCE_MOLTEN_AMBER, BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).speedFactor(0.5F).lightLevel(blockState -> 0)));
    public static final RegistrySupplier<Block> AMBER_CAULDRON = createBlockNoItem("amber_cauldron", () -> new AmberLayeredCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)));

    //Gem Lanterns
    public static final RegistrySupplier<Block> AMBER_LANTERN = createBlock("amber_lantern", () -> new SolidAmberBlock(SMProperties.Blocks.AMBER_BUILDING_BLOCKS.lightLevel(state -> 15)));
    public static final RegistrySupplier<Block> JADE_LANTERN = createBlock("jade_lantern", () -> new Block(SMProperties.Blocks.JADE_BLOCKS.lightLevel(state -> 15)));
    public static final RegistrySupplier<Block> DIAMOND_LANTERN = createBlock("diamond_lantern", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).lightLevel(state -> 15)));
    public static final RegistrySupplier<Block> EMERALD_LANTERN = createBlock("emerald_lantern", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.EMERALD_BLOCK).lightLevel(state -> 15)));
    public static final RegistrySupplier<Block> LAPIS_LANTERN = createBlock("lapis_lantern", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_BLOCK).lightLevel(state -> 15)));
    public static final RegistrySupplier<Block> AMETHYST_LANTERN = createBlock("amethyst_lantern", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).lightLevel(state -> 15)));
    public static final RegistrySupplier<Block> QUARTZ_LANTERN = createBlock("quartz_lantern", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).lightLevel(state -> 15)));

    //Petrified Wood
    public static final RegistrySupplier<Block> PETRIFIED_PLANKS = createBlock("petrified_planks", () -> new Block(SMProperties.Blocks.petrified().requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> STRIPPED_PETRIFIED_LOG = createBlock("stripped_petrified_log", () -> strippedLog(MapColor.TERRACOTTA_ORANGE, MapColor.TERRACOTTA_ORANGE));
    public static final RegistrySupplier<Block> PETRIFIED_LOG = createBlock("petrified_log", () -> log(STRIPPED_PETRIFIED_LOG, MapColor.TERRACOTTA_ORANGE, MapColor.COLOR_BROWN));
    public static final RegistrySupplier<Block> STRIPPED_PETRIFIED_WOOD = createBlock("stripped_petrified_wood", () -> new RotatedPillarBlock(SMProperties.Blocks.petrified().requiresCorrectToolForDrops().mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> PETRIFIED_WOOD = createBlock("petrified_wood", () -> new PetrifiedLog(STRIPPED_PETRIFIED_WOOD, SMProperties.Blocks.petrified().requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BROWN)));
    public static final Pair<RegistrySupplier<StandingSignBlock>, RegistrySupplier<WallSignBlock>> PETRIFIED_SIGN = createSignBlock("petrified", SMBlocksetTypes.PETRIFIED_WOOD_TYPE.get(), SMProperties.Blocks.PETRIFIED_SIGNS);
    public static final Pair<RegistrySupplier<CeilingHangingSignBlock>, RegistrySupplier<WallHangingSignBlock>> PETRIFIED_HANGING_SIGN = createHangingSignBlock("petrified", SMBlocksetTypes.PETRIFIED_WOOD_TYPE.get(), SMProperties.Blocks.PETRIFIED_SIGNS);
    public static final RegistrySupplier<Block> PETRIFIED_PRESSURE_PLATE = createBlock("petrified_pressure_plate", () -> new PressurePlateBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), SMProperties.Blocks.PETRIFIED_PRESSURE_PLATE));
    public static final RegistrySupplier<Block> PETRIFIED_TRAPDOOR = createBlock("petrified_trapdoor", () -> new TrapDoorBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), SMProperties.Blocks.PETRIFIED_TRAPDOOR));
    public static final RegistrySupplier<Block> PETRIFIED_STAIRS = createBlock("petrified_stairs", () -> new StairBlock(PETRIFIED_PLANKS.get().defaultBlockState(), SMProperties.Blocks.petrified().requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> PETRIFIED_SLAB = createBlock("petrified_slab", () -> new SlabBlock(SMProperties.Blocks.petrified().requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> PETRIFIED_BUTTON = createBlock("petrified_button", () -> new ButtonBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), 20, SMProperties.Blocks.PETRIFIED_BUTTON));
    public static final RegistrySupplier<Block> PETRIFIED_FENCE_GATE = createBlock("petrified_fence_gate", () -> new FenceGateBlock(SMBlocksetTypes.PETRIFIED_WOOD_TYPE.get(), SMProperties.Blocks.petrified().requiresCorrectToolForDrops().forceSolidOn()));
    public static final RegistrySupplier<Block> PETRIFIED_FENCE = createBlock("petrified_fence", () -> new FenceBlock(SMProperties.Blocks.petrified().requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> PETRIFIED_DOOR = createBlock("petrified_door", () -> new DoorBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), SMProperties.Blocks.petrified().requiresCorrectToolForDrops().noOcclusion().strength(0.35F)));
    public static final RegistrySupplier<Block> PETRIFIED_SAPLING = createBlock("petrified_sapling", () -> new PetrifiedSapling(SMProperties.Blocks.PETRIFIED_SAPLING));
    public static final RegistrySupplier<Block> POTTED_PETRIFIED_SAPLING = createBlockNoItem("potted_petrified_sapling", () -> new FlowerPotBlock(PETRIFIED_SAPLING.get(), FLOWER_POT));

    //Misc
    public static final RegistrySupplier<Block> TORTOISE_EGG = createBlock("tortoise_egg", () -> new TortoiseEggBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG)));
    public static final RegistrySupplier<Block> ITEM_STAND = createBlock("item_stand", () -> new ItemStandBlock(SMProperties.Blocks.ITEM_STAND));

    //Ancient Skulls
    public static List<Supplier<Block>> ANCIENT_SKULLS = new ArrayList<>();
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> CRACKED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.CRACKED, "The head of a giant ancient creature, it has a noticeable amount of cracks", 43);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> CRESTED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.CRESTED, "The large head of a now extinct animal, the beak seems more hollow than others", 40);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> FLATBILLED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.FLATBILLED, "The head of an animal that went extinct long ago", 37);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> GIGANTIC_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.GIGANTIC, "The gigantic head of an ancient creature, it feels familiar", 43);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> HORNED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.HORNED, "The head of an extinct creature with a broken off horn on its head", 36);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> LONG_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.LONG, "A long head from an extinct animals, it has a large overbite", 34);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> TINY_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.TINY, "The head of a small chicken sized critter that lived long ago", 30);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> WIDE_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.WIDE, "The head of an extinct animal with a strangely wide head and giant eye sockets", 37);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> RIBBED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.RIBBED, "Has a small ribcage directly attached to the skull", 37);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> UNICORN_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.UNICORN, "A normal horse skull, but with a large horn protruding from it", 25);

    public static Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> registerAncientSkull(AncientSkullBlock.Types type, String description, int price) {
        String typeName = SMTextUtil.convertSkullTypeToString(type);
        String skullName = typeName + "_ancient_skull";
        RegistrySupplier<Block> skull = createBlockNoItemNoLang(skullName, () -> new AncientSkullBlock(type, SMProperties.Blocks.ancientSkulls()));
        RegistrySupplier<Block> wallSkull = createBlockNoItemNoLang(typeName + "_ancient_wall_skull", () -> new AncientWallSkullBlock(type, SMProperties.Blocks.ancientSkulls().overrideLootTable(optionalBlockLoot(skull))));
        ANCIENT_SKULLS.add(skull);
        RegistrySupplier<Item> skullItem = ITEMS.register(location(skullName), () -> new StandingAndWallBlockItem(skull.get(), wallSkull.get(), Direction.DOWN, SMProperties.Items.artifacts()));
        SMItems.ARTIFACT_DESC_MAP.put(skullItem, SMTextUtil.addSMTranslatable("artifact." + skullName + ".desc", description).withStyle(SMTextDefinitions.ARTIFACT_DESC_STYLE));
        SMItems.TRADES.put(skullItem, price);

        return Pair.of(skull, wallSkull);
    }

    private static PetrifiedLog log(Supplier<Block> strippedBlock, MapColor pTopMapColor, MapColor pSideMapColor) {
        return new PetrifiedLog(strippedBlock, SMProperties.Blocks.petrified().requiresCorrectToolForDrops().mapColor((blockState) ->
                blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor));
    }

    private static RotatedPillarBlock strippedLog(MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(SMProperties.Blocks.petrified().requiresCorrectToolForDrops().mapColor((blockState) ->
                blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor));
    }

    public static Pair<RegistrySupplier<StandingSignBlock>, RegistrySupplier<WallSignBlock>> createSignBlock(String name, WoodType woodType, Block.Properties properties) {
        RegistrySupplier<StandingSignBlock> standing = createBlockNoItem(name + "_sign", () -> new StandingSignBlock(woodType, properties));
        RegistrySupplier<WallSignBlock> wall = createBlockNoItemNoLang(name + "_wall_sign", () -> new WallSignBlock(woodType, properties.overrideLootTable(optionalBlockLoot(standing))));
        ITEMS.register(location(name + "_sign"), () -> new SignItem(standing.get(), wall.get(), new Item.Properties()));
        return Pair.of(standing, wall);
    }
    public static Pair<RegistrySupplier<CeilingHangingSignBlock>, RegistrySupplier<WallHangingSignBlock>> createHangingSignBlock(String name, WoodType woodType, Block.Properties properties) {
        RegistrySupplier<CeilingHangingSignBlock> ceiling = createBlockNoItem(name + "_hanging_sign", () -> new CeilingHangingSignBlock(woodType, properties));
        RegistrySupplier<WallHangingSignBlock> wall = createBlockNoItemNoLang(name + "_wall_hanging_sign", () -> new WallHangingSignBlock(woodType, properties.overrideLootTable(optionalBlockLoot(ceiling))));
        ITEMS.register(location(name + "_hanging_sign"), () -> new HangingSignItem(ceiling.get(), wall.get(), new Item.Properties()));
        return Pair.of(ceiling, wall);
    }

    private static <B extends Block> RegistrySupplier<B> createBlockNoItemNoLang(String name, Supplier<B> supplier) {
        return BLOCKS.register(location(name), supplier);
    }

    private static <B extends Block> RegistrySupplier<B> createBlockNoItem(String name, Supplier<B> supplier) {
        RegistrySupplier<B> block = createBlockNoItemNoLang(name, supplier);
        AUTO_TRANSLATE.add(block);
        return block;
    }
    
    private static <B extends Block> RegistrySupplier<B> createBlockNoLang(String name, Supplier<B> supplier) {
        RegistrySupplier<B> block = createBlockNoItemNoLang(name, supplier);
        ITEMS.register(location(name), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> RegistrySupplier<B> createBlock(String name, Supplier<B> supplier) {
        RegistrySupplier<B> block = createBlockNoLang(name, supplier);
        AUTO_TRANSLATE.add(block);
        return block;
    }

    public static void init() {
        registerWaxableWeatheringBlocks(COPPER_BUTTON, EXPOSED_COPPER_BUTTON, WAXED_COPPER_BUTTON);
        registerWaxableWeatheringBlocks(EXPOSED_COPPER_BUTTON, WEATHERED_COPPER_BUTTON, WAXED_EXPOSED_COPPER_BUTTON);
        registerWaxableWeatheringBlocks(WEATHERED_COPPER_BUTTON, OXIDIZED_COPPER_BUTTON, WAXED_WEATHERED_COPPER_BUTTON);
        registerWaxableWeatheringBlocks(OXIDIZED_COPPER_BUTTON, null, WAXED_OXIDIZED_COPPER_BUTTON);
    }

    public static void registerWaxableWeatheringBlocks(RegistrySupplier<Block> unWaxedBlock, @Nullable RegistrySupplier<Block> weatheredBlock, RegistrySupplier<Block> waxedBlock) {
        if (weatheredBlock != null) registerWeatheringBlockPair(unWaxedBlock, weatheredBlock);
        registerWaxableBlockPair(waxedBlock, unWaxedBlock);
    }

    @ExpectPlatform
    static void registerWeatheringBlockPair(RegistrySupplier<Block> from, RegistrySupplier<Block> to) {
        throw new UnsupportedOperationException("This method should be replaced by Architectury");
    }
    @ExpectPlatform
    static void registerWaxableBlockPair(RegistrySupplier<Block> from, RegistrySupplier<Block> to) {
        throw new UnsupportedOperationException("This method should be replaced by Architectury");
    }
    public static void registerCauldronBlocks() {
        registerCauldron(AMBER_CAULDRON, SMFluids.SOURCE_MOLTEN_AMBER, BlockStateProperties.LEVEL_CAULDRON);
    }
    static void registerCauldron(RegistrySupplier<Block> cauldronBlock, RegistrySupplier<? extends Fluid> fluid, IntegerProperty levelProperty) {
        CAULDRON_MAP.add(new CauldronMap(cauldronBlock, fluid, levelProperty));
    }

    public static ArrayList<CauldronMap> CAULDRON_MAP = new ArrayList<>();
    public record CauldronMap(RegistrySupplier<Block> cauldronBlock, RegistrySupplier<? extends Fluid> fluid, IntegerProperty levelProperty) { }

}
