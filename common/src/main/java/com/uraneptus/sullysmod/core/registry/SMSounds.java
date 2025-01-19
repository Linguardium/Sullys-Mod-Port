package com.uraneptus.sullysmod.core.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.SoundType;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.key;
import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.SOUNDS;


public class SMSounds {

    //Music Discs
    public static final RegistrySupplier<SoundEvent> MUSIC_DISC_SCOUR = registerSimpleSoundEvent("music_disc.scour");
    public static final RegistrySupplier<SoundEvent> MUSIC_DISC_SUNKEN_PAST = registerSimpleSoundEvent("music_disc.sunken_past");
    public static final ResourceKey<JukeboxSong> JUKEBOX_SCOUR = key(Registries.JUKEBOX_SONG, "scour");
    public static final ResourceKey<JukeboxSong> JUKEBOX_SUNKEN_PAST = key(Registries.JUKEBOX_SONG, "sunken_past");
    //Item Sounds
    public static final RegistrySupplier<SoundEvent> POLISH_JADE = registerSimpleSoundEvent("block.grindstone.polish_jade");
    public static final RegistrySupplier<SoundEvent> VIAL_SHATTERS = registerSimpleSoundEvent("item.vial.shatter");
    public static final RegistrySupplier<SoundEvent> VIAL_FILLS = registerSimpleSoundEvent("item.vial.fill");
    public static final RegistrySupplier<SoundEvent> THROWING_KNIFE_HIT = registerSimpleSoundEvent("item.throwing_knife.hit");
    public static final RegistrySupplier<SoundEvent> THROWING_KNIFE_HIT_GROUND = registerSimpleSoundEvent("item.throwing_knife.hit_ground");
    public static final RegistrySupplier<SoundEvent> THROWING_KNIFE_THROW = registerSimpleSoundEvent("item.throwing_knife.throw");
    public static final RegistrySupplier<SoundEvent> BROKEN_BOTTLE_SHATTERS = registerSimpleSoundEvent("item.broken_bottle.shatter");

    //Block Sounds
    public static final RegistrySupplier<SoundEvent> JADE_RICOCHET = registerSimpleSoundEvent("block.jade.ricochet");
    public static final RegistrySupplier<SoundEvent> FLINGER_FLINGS = registerSimpleSoundEvent("block.flinger_totem.shoot");
    public static final RegistrySupplier<SoundEvent> FLINGER_INPUT_HONEY = registerSimpleSoundEvent("block.flinger_totem.input_honey");
    public static final RegistrySupplier<SoundEvent> FLINGER_ADD_HONEY = registerSimpleSoundEvent("block.flinger_totem.add_honey");
    public static final RegistrySupplier<SoundEvent> FLINGER_REDUCE_HONEY = registerSimpleSoundEvent("block.flinger_totem.reduce_honey");
    public static final RegistrySupplier<SoundEvent> PETRIFIED_WOOD_BREAK = registerSimpleSoundEvent("block.petrified_wood.break");
    public static final RegistrySupplier<SoundEvent> PETRIFIED_WOOD_FALL = registerSimpleSoundEvent("block.petrified_wood.fall");
    public static final RegistrySupplier<SoundEvent> PETRIFIED_WOOD_HIT = registerSimpleSoundEvent("block.petrified_wood.hit");
    public static final RegistrySupplier<SoundEvent> PETRIFIED_WOOD_PLACE = registerSimpleSoundEvent("block.petrified_wood.place");
    public static final RegistrySupplier<SoundEvent> PETRIFIED_WOOD_STEP = registerSimpleSoundEvent("block.petrified_wood.step");
    public static final RegistrySupplier<SoundEvent> COPPER_BUTTON_CLICK_OFF = registerSimpleSoundEvent("block.copper_button.click_off");
    public static final RegistrySupplier<SoundEvent> COPPER_BUTTON_CLICK_ON = registerSimpleSoundEvent("block.copper_button.click_on");
    public static final RegistrySupplier<SoundEvent> AMBER_DRIP = registerSimpleSoundEvent("block.amber.drip");

    //Note Block Instruments
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_CRESTED_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.crested");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_CRACKED_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.cracked");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_FLATBILLED_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.flatbilled");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_GIGANTIC_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.gigantic");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_HORNED_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.horned");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_LONG_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.long");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_TINY_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.tiny");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_WIDE_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.wide");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_RIBBED_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.ribbed");
    public static final RegistrySupplier<SoundEvent> NOTE_BLOCK_UNICORN_SKULL = registerSimpleSoundEvent("block.note_block.ancient_skull.unicorn");

    //Entity Sounds
    public static final RegistrySupplier<SoundEvent> TORTOISE_HURT = registerSimpleSoundEvent("entity.tortoise.hurt");
    public static final RegistrySupplier<SoundEvent> BABY_TORTOISE_HURT = registerSimpleSoundEvent("entity.tortoise.hurt_baby");
    public static final RegistrySupplier<SoundEvent> TORTOISE_DEATH = registerSimpleSoundEvent("entity.tortoise.death");
    public static final RegistrySupplier<SoundEvent> BABY_TORTOISE_DEATH = registerSimpleSoundEvent("entity.tortoise.death_baby");
    public static final RegistrySupplier<SoundEvent> TORTOISE_AMBIENT = registerSimpleSoundEvent("entity.tortoise.ambient");
    public static final RegistrySupplier<SoundEvent> TORTOISE_HIDE = registerSimpleSoundEvent("entity.tortoise.hide");
    public static final RegistrySupplier<SoundEvent> TORTOISE_EMERGE = registerSimpleSoundEvent("entity.tortoise.emerge");
    public static final RegistrySupplier<SoundEvent> TORTOISE_HURT_HIDDEN = registerSimpleSoundEvent("entity.tortoise.hurt.hidden");
    public static final RegistrySupplier<SoundEvent> TORTOISE_LAY_EGG = registerSimpleSoundEvent("entity.tortoise.lay_egg");
    public static final RegistrySupplier<SoundEvent> TORTOISE_EGG_BREAK = registerSimpleSoundEvent("entity.tortoise.egg_break");
    public static final RegistrySupplier<SoundEvent> TORTOISE_EGG_CRACK = registerSimpleSoundEvent("entity.tortoise.egg_crack");
    public static final RegistrySupplier<SoundEvent> TORTOISE_EGG_HATCH = registerSimpleSoundEvent("entity.tortoise.egg_hatch");

    public static final RegistrySupplier<SoundEvent> TORTOISE_SHELL_PLACE = registerSimpleSoundEvent("entity.tortoise_shell.place");

    public static final RegistrySupplier<SoundEvent> LANTERNFISH_FLOP = registerSimpleSoundEvent("entity.lanternfish.flop");
    public static final RegistrySupplier<SoundEvent> LANTERNFISH_HURT = registerSimpleSoundEvent("entity.lanternfish.hurt");
    public static final RegistrySupplier<SoundEvent> LANTERNFISH_DEATH = registerSimpleSoundEvent("entity.lanternfish.death");
    public static final RegistrySupplier<SoundEvent> PIRANHA_FLOP = registerSimpleSoundEvent("entity.piranha.flop");
    public static final RegistrySupplier<SoundEvent> PIRANHA_HURT = registerSimpleSoundEvent("entity.piranha.hurt");
    public static final RegistrySupplier<SoundEvent> PIRANHA_DEATH = registerSimpleSoundEvent("entity.piranha.death");

    public static final RegistrySupplier<SoundEvent> BOULDERING_ZOMBIE_AMBIENT = registerSimpleSoundEvent("entity.bouldering_zombie.ambient");
    public static final RegistrySupplier<SoundEvent> BOULDERING_ZOMBIE_HURT = registerSimpleSoundEvent("entity.bouldering_zombie.hurt");
    public static final RegistrySupplier<SoundEvent> BOULDERING_ZOMBIE_DEATH = registerSimpleSoundEvent("entity.bouldering_zombie.death");

    //Equip sounds
    public static final RegistrySupplier<SoundEvent> EQUIP_MINERS_HELMET = registerSimpleSoundEvent("item.armor.equip_miners_helmet");
    public static final RegistrySupplier<SoundEvent> EQUIP_SMALL_DENTED_HELMET = registerSimpleSoundEvent("item.armor.equip_small_dented_helmet");
    public static final RegistrySupplier<SoundEvent> EQUIP_LOST_CROWN = registerSimpleSoundEvent("item.armor.equip_lost_crown");

    //Ambient sounds
    public static final RegistrySupplier<SoundEvent> MOUNTAIN_CALLS = registerSimpleSoundEvent("ambient.mountain.calls");

    //SoundTypes
    public static final SoundType PETRIFIED_WOOD = new SoundType(1.0F, 1.0F, PETRIFIED_WOOD_BREAK.get(), PETRIFIED_WOOD_STEP.get(), PETRIFIED_WOOD_PLACE.get(), PETRIFIED_WOOD_HIT.get(), PETRIFIED_WOOD_FALL.get());
    private static RegistrySupplier<SoundEvent> registerSimpleSoundEvent(String id) {
        return SOUNDS.register(location(id), ()->SoundEvent.createVariableRangeEvent(location(id)));
        
    }

    public static void init() { }

}
