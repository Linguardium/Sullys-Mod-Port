package com.uraneptus.sullysmod.core.other;

import com.uraneptus.sullysmod.core.other.tags.SMItemTags;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static com.uraneptus.sullysmod.common.items.JadeShieldItem.createJadeShieldModifiers;
import static net.minecraft.world.item.component.Consumables.defaultFood;

public class SMProperties {

    public static final class Blocks {
        public static final BlockBehaviour.Properties JADE_ORE = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F);
        public static final BlockBehaviour.Properties DEEPSLATE_JADE_ORE = BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE);
        public static final BlockBehaviour.Properties ROUGH_JADE_BLOCKS = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F);
        public static final BlockBehaviour.Properties JADE_BLOCKS = BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F);
        public static final BlockBehaviour.Properties COPPER_BUTTONS = BlockBehaviour.Properties.of().noCollission().strength(0.5F);

        public static final BlockBehaviour.Properties AMBER = BlockBehaviour.Properties.of().strength(1.3F, 3F).mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().randomTicks().noOcclusion().dynamicShape().isViewBlocking((state, pLevel, pos) -> true);
        public static final BlockBehaviour.Properties AMBER_BUILDING_BLOCKS = BlockBehaviour.Properties.of().strength(1.3F, 3F).mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().randomTicks().dynamicShape().forceSolidOn().isViewBlocking((state, pLevel, pos) -> true);
        public static final BlockBehaviour.Properties ITEM_STAND = BlockBehaviour.Properties.of().strength(2.0F, 6.0F).requiresCorrectToolForDrops().noOcclusion();
        public static BlockBehaviour.Properties ancientSkulls() {
            return BlockBehaviour.Properties.of().strength(1.0F).pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.CUSTOM_HEAD);
        }

        public static BlockBehaviour.Properties petrified() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F, 6.0F).sound(SMSounds.PETRIFIED_WOOD).mapColor(MapColor.TERRACOTTA_ORANGE);
        }
        public static final BlockBehaviour.Properties PETRIFIED_TRAPDOOR = petrified().requiresCorrectToolForDrops().noOcclusion().isValidSpawn(SMPropertyUtil::never).strength(3.0F);
        public static final BlockBehaviour.Properties PETRIFIED_PRESSURE_PLATE = petrified().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties PETRIFIED_BUTTON = petrified().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties PETRIFIED_SIGNS = petrified().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties PETRIFIED_SAPLING = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);

        public static final BlockBehaviour.Properties FLOWER_POT = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
    }

    public static final class Items {
        //Rarity


        //Item Specific
        public static final Item.Properties MUSIC_DISCS = singleStack().rarity(Rarity.RARE);
        public static final Item.Properties JADE_SHIELD = jadeProperties().durability(400).attributes(createJadeShieldModifiers(-2.0f));
        public static Item.Properties ancientSkull(Item.Properties properties, SoundEvent customSound) {
            if (customSound == null) return properties;
            return properties.component(DataComponents.NOTE_BLOCK_SOUND, customSound.location());
        }


                                                   // TODO: Additional Rarities
        public static Item.Properties artifacts() {
            return new Item.Properties();//.rarity(SMItems.ANCIENT);
        }

        public static Item.Properties sixteenStack() {
            return new Item.Properties().stacksTo(16);
        }

        public static Item.Properties singleStack() {
            return new Item.Properties().stacksTo(1);
        }
    }

    public static final class Foods {
        public static final FoodProperties LANTERNFISH_FOOD = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build();
        public static final FoodProperties COOKED_LANTERNFISH_FOOD = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6F).build();
        public static final FoodProperties PIRANHA_FOOD = new FoodProperties.Builder().nutrition(3).saturationModifier(0.1F).build();
        public static final FoodProperties COOKED_PIRANHA_FOOD = new FoodProperties.Builder().nutrition(7).saturationModifier(0.5F).build();
        public static final FoodProperties PETRIFIED_COOKIE = new FoodProperties.Builder().build();
    }
    public static final class Consumables {
        public static final Consumable LANTERNFISH_FOOD = defaultFood()
                .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0), 1.0F))
                .build();
        public static final Consumable COOKED_LANTERNFISH_FOOD = defaultFood()
                .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.GLOWING, 200), 1.0F)).build();
    }
    public static Item.Properties jadeProperties() {
        return new Item.Properties().repairable(SMItemTags.JADE_GEM);
    }
    public static final Item.Properties stacksOnce() {
        return new Item.Properties().stacksTo(1);
    }
}
