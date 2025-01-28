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
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
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

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.*;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.BLOCKS;


public class SMBlocks {
    public static List<RegistrySupplier<? extends Block>> AUTO_TRANSLATE = new ArrayList<>();

    //Jade
    public static final RegistrySupplier<Block> JADE_ORE = createBlock("jade_ore", properties -> new DropExperienceBlock(UniformInt.of(0, 2), properties), SMProperties.Blocks.JADE_ORE);
    public static final RegistrySupplier<Block> DEEPSLATE_JADE_ORE = createBlock("deepslate_jade_ore", properties-> new DropExperienceBlock(UniformInt.of(0, 2), properties), SMProperties.Blocks.DEEPSLATE_JADE_ORE);
    public static final RegistrySupplier<Block> ROUGH_JADE_BLOCK = createBlockNoLang("rough_jade_block", Block::new, SMProperties.Blocks.ROUGH_JADE_BLOCKS);
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICKS = createSimpleBlock("rough_jade_bricks", SMProperties.Blocks.ROUGH_JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_BLOCK = createBlockNoLang("jade_block", Block::new, SMProperties.Blocks.JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_BRICKS = createBlock("jade_bricks", Block::new, SMProperties.Blocks.JADE_BLOCKS);
    public static final RegistrySupplier<Block> CHISELED_JADE = createBlock("chiseled_jade", Block::new, SMProperties.Blocks.JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_TOTEM = createBlock("jade_totem", SMDirectionalBlock::new, SMProperties.Blocks.JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_FLINGER_TOTEM = createBlock("jade_flinger_totem", FlingerTotem::new, SMProperties.Blocks.JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_PILLAR = createBlock("jade_pillar", RotatedPillarBlock::new, SMProperties.Blocks.JADE_BLOCKS);

    //Jade Stairs
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICK_STAIRS = createBlock("rough_jade_brick_stairs", properties -> new StairBlock(ROUGH_JADE_BRICKS.get().defaultBlockState(), properties), SMProperties.Blocks.ROUGH_JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_BRICK_STAIRS = createBlock("jade_brick_stairs", properties -> new StairBlock(JADE_BRICKS.get().defaultBlockState(), properties),  SMProperties.Blocks.JADE_BLOCKS);

    //Jade Slabs
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICK_SLAB = createBlock("rough_jade_brick_slab", SlabBlock::new, SMProperties.Blocks.ROUGH_JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_BRICK_SLAB = createBlock("jade_brick_slab", SlabBlock::new, SMProperties.Blocks.JADE_BLOCKS);

    //Jade Walls
    public static final RegistrySupplier<Block> ROUGH_JADE_BRICK_WALL = createBlock("rough_jade_brick_wall", WallBlock::new, SMProperties.Blocks.ROUGH_JADE_BLOCKS);
    public static final RegistrySupplier<Block> JADE_BRICK_WALL = createBlock("jade_brick_wall", WallBlock::new, SMProperties.Blocks.JADE_BLOCKS);


    //Copper Buttons
    public static final RegistrySupplier<Block> COPPER_BUTTON = createBlock("copper_button", (properties) -> new WeatheringCopperButtonBlock(properties, BlockSetType.COPPER, 10, WeatheringCopper.WeatherState.UNAFFECTED), SMProperties.Blocks.COPPER_BUTTONS);
    public static final RegistrySupplier<Block> EXPOSED_COPPER_BUTTON = createBlock("exposed_copper_button", (properties) -> new WeatheringCopperButtonBlock(properties, BlockSetType.COPPER, 20, WeatheringCopper.WeatherState.EXPOSED), SMProperties.Blocks.COPPER_BUTTONS);
    public static final RegistrySupplier<Block> WEATHERED_COPPER_BUTTON = createBlock("weathered_copper_button", (properties) -> new WeatheringCopperButtonBlock(properties, BlockSetType.COPPER, 30, WeatheringCopper.WeatherState.WEATHERED), SMProperties.Blocks.COPPER_BUTTONS);
    public static final RegistrySupplier<Block> OXIDIZED_COPPER_BUTTON = createBlock("oxidized_copper_button", (properties) -> new WeatheringCopperButtonBlock(properties, BlockSetType.COPPER, 40, WeatheringCopper.WeatherState.OXIDIZED), SMProperties.Blocks.COPPER_BUTTONS);
    public static final RegistrySupplier<Block> WAXED_COPPER_BUTTON = createBlock("waxed_copper_button", (properties) -> new CopperButtonBlock(properties, BlockSetType.COPPER, 10), SMProperties.Blocks.COPPER_BUTTONS);
    public static final RegistrySupplier<Block> WAXED_EXPOSED_COPPER_BUTTON = createBlock("waxed_exposed_copper_button", (properties) -> new CopperButtonBlock(properties, BlockSetType.COPPER, 20), SMProperties.Blocks.COPPER_BUTTONS);
    public static final RegistrySupplier<Block> WAXED_WEATHERED_COPPER_BUTTON = createBlock("waxed_weathered_copper_button", (properties) -> new CopperButtonBlock(properties, BlockSetType.COPPER, 30), SMProperties.Blocks.COPPER_BUTTONS);
    public static final RegistrySupplier<Block> WAXED_OXIDIZED_COPPER_BUTTON = createBlock("waxed_oxidized_copper_button", (properties) -> new CopperButtonBlock(properties, BlockSetType.COPPER, 40), SMProperties.Blocks.COPPER_BUTTONS);

    //Amber
    public static final RegistrySupplier<Block> AMBER = createBlock("amber", AmberBlock::new, SMProperties.Blocks.AMBER);
    public static final RegistrySupplier<Block> AMBER_BRICKS = createBlock("amber_bricks", SolidAmberBlock::new, SMProperties.Blocks.AMBER_BUILDING_BLOCKS);
    public static final RegistrySupplier<Block> AMBER_BRICK_STAIRS = createBlock("amber_brick_stairs", properties -> new AmberStairBlock(() -> AMBER_BRICKS.get().defaultBlockState(), properties), SMProperties.Blocks.AMBER_BUILDING_BLOCKS);
    public static final RegistrySupplier<Block> AMBER_BRICK_SLAB = createBlock("amber_brick_slab", AmberSlabBlock::new, SMProperties.Blocks.AMBER_BUILDING_BLOCKS);
    public static final RegistrySupplier<Block> AMBER_BRICK_WALL = createBlock("amber_brick_wall",AmberWallBlock::new, SMProperties.Blocks.AMBER_BUILDING_BLOCKS);
    public static final RegistrySupplier<Block> ROUGH_AMBER = createBlock("rough_amber", SolidAmberBlock::new, SMProperties.Blocks.AMBER_BUILDING_BLOCKS);
    public static final RegistrySupplier<Block> CHISELED_AMBER = createBlock("chiseled_amber", SolidAmberBlock::new, SMProperties.Blocks.AMBER_BUILDING_BLOCKS);
    public static final RegistrySupplier<Block> AMBER_PILLAR = createBlock("amber_pillar",AmberRotatedPillarBlock::new, SMProperties.Blocks.AMBER_BUILDING_BLOCKS);
    public static final RegistrySupplier<LiquidBlock> MOLTEN_AMBER_BLOCK = createBlockNoItem("molten_amber_block", (properties) -> new ArchitecturyLiquidBlock(SMFluids.SOURCE_MOLTEN_AMBER, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).speedFactor(0.5F).lightLevel(blockState -> 0));
    public static final RegistrySupplier<Block> AMBER_CAULDRON = createBlockNoItem("amber_cauldron", AmberLayeredCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));



    //Gem Lanterns
    public static final RegistrySupplier<Block> AMBER_LANTERN = createBlock("amber_lantern", SolidAmberBlock::new, SMProperties.Blocks.AMBER_BUILDING_BLOCKS.lightLevel(state -> 15));
    public static final RegistrySupplier<Block> JADE_LANTERN = createSimpleBlock("jade_lantern",SMProperties.Blocks.JADE_BLOCKS.lightLevel(state -> 15));
    public static final RegistrySupplier<Block> DIAMOND_LANTERN = createSimpleBlock("diamond_lantern",BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).lightLevel(state -> 15));
    public static final RegistrySupplier<Block> EMERALD_LANTERN = createSimpleBlock("emerald_lantern", BlockBehaviour.Properties.ofFullCopy(Blocks.EMERALD_BLOCK).lightLevel(state -> 15));
    public static final RegistrySupplier<Block> LAPIS_LANTERN = createSimpleBlock("lapis_lantern", BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_BLOCK).lightLevel(state -> 15));
    public static final RegistrySupplier<Block> AMETHYST_LANTERN = createSimpleBlock("amethyst_lantern", BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).lightLevel(state -> 15));
    public static final RegistrySupplier<Block> QUARTZ_LANTERN = createSimpleBlock("quartz_lantern", BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).lightLevel(state -> 15));

    //Petrified Wood
    public static final RegistrySupplier<Block> PETRIFIED_PLANKS = createSimpleBlock("petrified_planks", SMProperties.Blocks.petrified().requiresCorrectToolForDrops());
    public static final RegistrySupplier<Block> STRIPPED_PETRIFIED_LOG = createBlock("stripped_petrified_log", strippedLog(MapColor.TERRACOTTA_ORANGE, MapColor.TERRACOTTA_ORANGE));
    public static final RegistrySupplier<Block> PETRIFIED_LOG = createBlock("petrified_log", log(STRIPPED_PETRIFIED_LOG, MapColor.TERRACOTTA_ORANGE, MapColor.COLOR_BROWN));
    public static final RegistrySupplier<Block> STRIPPED_PETRIFIED_WOOD = createBlock("stripped_petrified_wood", RotatedPillarBlock::new, SMProperties.Blocks.petrified().requiresCorrectToolForDrops().mapColor(MapColor.TERRACOTTA_ORANGE));
    public static final RegistrySupplier<Block> PETRIFIED_WOOD = createBlock("petrified_wood", log(STRIPPED_PETRIFIED_WOOD, MapColor.COLOR_BROWN, MapColor.COLOR_BROWN));
    public static final Pair<RegistrySupplier<StandingSignBlock>, RegistrySupplier<WallSignBlock>> PETRIFIED_SIGN = createSignBlock("petrified", SMBlocksetTypes.PETRIFIED_WOOD_TYPE.get(), SMProperties.Blocks.PETRIFIED_SIGNS);
    public static final Pair<RegistrySupplier<CeilingHangingSignBlock>, RegistrySupplier<WallHangingSignBlock>> PETRIFIED_HANGING_SIGN = createHangingSignBlock("petrified", SMBlocksetTypes.PETRIFIED_WOOD_TYPE.get(), SMProperties.Blocks.PETRIFIED_SIGNS);
    public static final RegistrySupplier<Block> PETRIFIED_PRESSURE_PLATE = createBlock("petrified_pressure_plate", properties -> new PressurePlateBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), properties), SMProperties.Blocks.PETRIFIED_PRESSURE_PLATE);
    public static final RegistrySupplier<Block> PETRIFIED_TRAPDOOR = createBlock("petrified_trapdoor", properties -> new TrapDoorBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), properties), SMProperties.Blocks.PETRIFIED_TRAPDOOR);
    public static final RegistrySupplier<Block> PETRIFIED_STAIRS = createBlock("petrified_stairs", properties -> new StairBlock(PETRIFIED_PLANKS.get().defaultBlockState(), properties), SMProperties.Blocks.petrified().requiresCorrectToolForDrops());
    public static final RegistrySupplier<Block> PETRIFIED_SLAB = createBlock("petrified_slab", SlabBlock::new, SMProperties.Blocks.petrified().requiresCorrectToolForDrops());
    public static final RegistrySupplier<Block> PETRIFIED_BUTTON = createBlock("petrified_button", properties -> new ButtonBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), 20, properties), SMProperties.Blocks.PETRIFIED_BUTTON);
    public static final RegistrySupplier<Block> PETRIFIED_FENCE_GATE = createBlock("petrified_fence_gate", properties -> new FenceGateBlock(SMBlocksetTypes.PETRIFIED_WOOD_TYPE.get(),  properties), SMProperties.Blocks.petrified().requiresCorrectToolForDrops().forceSolidOn());
    public static final RegistrySupplier<Block> PETRIFIED_FENCE = createBlock("petrified_fence", FenceBlock::new, SMProperties.Blocks.petrified().requiresCorrectToolForDrops());
    public static final RegistrySupplier<Block> PETRIFIED_DOOR = createBlock("petrified_door", properties -> new DoorBlock(SMBlocksetTypes.PETRIFIED_BLOCKSET.get(), properties), SMProperties.Blocks.petrified().requiresCorrectToolForDrops().noOcclusion().strength(0.35F));
    public static final RegistrySupplier<Block> PETRIFIED_SAPLING = createBlock("petrified_sapling", PetrifiedSapling::new, SMProperties.Blocks.PETRIFIED_SAPLING);
    public static final RegistrySupplier<Block> POTTED_PETRIFIED_SAPLING = createBlockNoItem("potted_petrified_sapling", properties -> new FlowerPotBlock(PETRIFIED_SAPLING.get(), properties), SMProperties.Blocks.FLOWER_POT);

    //Misc
    public static final RegistrySupplier<Block> TORTOISE_EGG = createBlock("tortoise_egg", TortoiseEggBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG));
    public static final RegistrySupplier<Block> ITEM_STAND = createBlock("item_stand", ItemStandBlock::new, SMProperties.Blocks.ITEM_STAND);

    //Ancient Skulls
    public static List<Supplier<Block>> ANCIENT_SKULLS = new ArrayList<>();
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> CRACKED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.CRACKED, SMSounds.NOTE_BLOCK_CRACKED_SKULL, "The head of a giant ancient creature, it has a noticeable amount of cracks", 43);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> CRESTED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.CRESTED, SMSounds.NOTE_BLOCK_CRESTED_SKULL, "The large head of a now extinct animal, the beak seems more hollow than others", 40);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> FLATBILLED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.FLATBILLED, SMSounds.NOTE_BLOCK_FLATBILLED_SKULL, "The head of an animal that went extinct long ago", 37);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> GIGANTIC_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.GIGANTIC, SMSounds.NOTE_BLOCK_GIGANTIC_SKULL, "The gigantic head of an ancient creature, it feels familiar", 43);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> HORNED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.HORNED, SMSounds.NOTE_BLOCK_HORNED_SKULL, "The head of an extinct creature with a broken off horn on its head", 36);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> LONG_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.LONG, SMSounds.NOTE_BLOCK_LONG_SKULL, "A long head from an extinct animals, it has a large overbite", 34);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> TINY_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.TINY, SMSounds.NOTE_BLOCK_TINY_SKULL, "The head of a small chicken sized critter that lived long ago", 30);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> WIDE_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.WIDE, SMSounds.NOTE_BLOCK_WIDE_SKULL, "The head of an extinct animal with a strangely wide head and giant eye sockets", 37);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> RIBBED_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.RIBBED, SMSounds.NOTE_BLOCK_RIBBED_SKULL, "Has a small ribcage directly attached to the skull", 37);
    public static final Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> UNICORN_ANCIENT_SKULL = registerAncientSkull(AncientSkullBlock.Types.UNICORN, SMSounds.NOTE_BLOCK_UNICORN_SKULL, "A normal horse skull, but with a large horn protruding from it", 25);

    public static Pair<RegistrySupplier<Block>, RegistrySupplier<Block>> registerAncientSkull(AncientSkullBlock.Types type, RegistrySupplier<SoundEvent> noteblockInstrument, String description, int price) {
        String typeName = SMTextUtil.convertSkullTypeToString(type);
        String skullName = typeName + "_ancient_skull";
        RegistrySupplier<Block> skull = createBlockNoItemNoLang(skullName, properties -> new AncientSkullBlock(type, noteblockInstrument, properties), SMProperties.Blocks.ancientSkulls());
        RegistrySupplier<Block> wallSkull = createBlockNoItemNoLang(typeName + "_ancient_wall_skull", properties -> new AncientWallSkullBlock(type, properties), SMProperties.Blocks.ancientSkulls().overrideLootTable(optionalBlockLoot(skull)));
        ANCIENT_SKULLS.add(skull);
        RegistrySupplier<Item> skullItem = SMItems.createItem(skullName, (properties) -> new StandingAndWallBlockItem(skull.get(), wallSkull.get(), Direction.DOWN, SMProperties.Items.ancientSkull(properties, noteblockInstrument.get())), SMProperties.Items.artifacts().useBlockDescriptionPrefix());
        SMItems.ARTIFACT_DESC_MAP.put(skullItem, SMTextUtil.addSMTranslatable("artifact." + skullName + ".desc", description).withStyle(SMTextDefinitions.ARTIFACT_DESC_STYLE));
        SMItems.TRADES.put(skullItem, price);

        return Pair.of(skull, wallSkull);
    }

    private static BlockFactory<Block> log(Supplier<Block> strippedBlock, MapColor pTopMapColor, MapColor pSideMapColor) {
        return properties->new PetrifiedLog(strippedBlock, SMProperties.Blocks.petrified(properties).requiresCorrectToolForDrops().mapColor((blockState) ->
                blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor));
    }

    private static BlockFactory<Block> strippedLog(MapColor pTopMapColor, MapColor pSideMapColor) {
        return properties->new RotatedPillarBlock(SMProperties.Blocks.petrified(properties).requiresCorrectToolForDrops().mapColor((blockState) ->
                blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor));
    }

    public static Pair<RegistrySupplier<StandingSignBlock>, RegistrySupplier<WallSignBlock>> createSignBlock(String name, WoodType woodType, Block.Properties properties) {
        RegistrySupplier<StandingSignBlock> standing = createBlockNoItem(name + "_sign", props -> new StandingSignBlock(woodType, props), properties);
        RegistrySupplier<WallSignBlock> wall = createBlockNoItemNoLang(name + "_wall_sign", props -> new WallSignBlock(woodType, props.overrideLootTable(optionalBlockLoot(standing))), properties);
        SMItems.createItem(name + "_sign", itemProperties -> new SignItem(standing.get(), wall.get(), itemProperties), new Item.Properties().useBlockDescriptionPrefix());
        return Pair.of(standing, wall);
    }
    public static Pair<RegistrySupplier<CeilingHangingSignBlock>, RegistrySupplier<WallHangingSignBlock>> createHangingSignBlock(String name, WoodType woodType, Block.Properties properties) {
        RegistrySupplier<CeilingHangingSignBlock> ceiling = createBlockNoItem(name + "_hanging_sign", props -> new CeilingHangingSignBlock(woodType, props), properties);
        RegistrySupplier<WallHangingSignBlock> wall = createBlockNoItemNoLang(name + "_wall_hanging_sign", props -> new WallHangingSignBlock(woodType, props.overrideLootTable(optionalBlockLoot(ceiling))), properties);
        SMItems.createItem(name+"_hanging_sign", itemProperties -> new HangingSignItem(ceiling.get(), wall.get(), itemProperties), new Item.Properties().useBlockDescriptionPrefix());
        return Pair.of(ceiling, wall);
    }

    private static <B extends Block> RegistrySupplier<B> createBlockNoItemNoLang(String name, BlockFactory<B> factory, Block.Properties properties) {
        ResourceKey<Block> key = key(Registries.BLOCK, name);
        return BLOCKS.register(location(name), ()->factory.create(properties.setId(key)));
    }

    private static <B extends Block> RegistrySupplier<B> createBlockNoItem(String name, BlockFactory<B> factory, Block.Properties properties) {
        RegistrySupplier<B> block = createBlockNoItemNoLang(name, factory, properties);
        AUTO_TRANSLATE.add(block);
        return block;
    }
    
    private static <B extends Block> RegistrySupplier<B> createBlockNoLang(String name, BlockFactory<B> factory, Block.Properties properties) {

        RegistrySupplier<B> block = createBlockNoItemNoLang(name, factory, properties);
        SMItems.createItem(name, itemProperties -> new BlockItem(block.get(), itemProperties), new Item.Properties().useBlockDescriptionPrefix());
        return block;
    }

    private static <B extends Block> RegistrySupplier<B> createBlock(String name, BlockFactory<B> factory) {
        return createBlock(name, factory, BlockBehaviour.Properties.of());
    }
    private static <B extends Block> RegistrySupplier<B> createBlock(String name, BlockFactory<B> factory, Block.Properties properties) {
        RegistrySupplier<B> block = createBlockNoLang(name, factory, properties);
        AUTO_TRANSLATE.add(block);
        return block;
    }
    private static RegistrySupplier<Block> createSimpleBlock(String name, Block.Properties properties) {
        RegistrySupplier<Block> block = createBlockNoLang(name, Block::new, properties);
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

    @FunctionalInterface
    public interface BlockFactory<T extends Block> {
        T create(BlockBehaviour.Properties properties);
    }
}
