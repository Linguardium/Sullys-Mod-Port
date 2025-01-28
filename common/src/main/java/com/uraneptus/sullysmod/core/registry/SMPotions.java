package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.core.other.DatagenDependentHashMap;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

import java.util.Map;
import java.util.function.Supplier;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.POTIONS;

public class SMPotions {
    public static Map<Supplier<? extends Potion>, String> POTION_TRANSLATIONS = new DatagenDependentHashMap<>();

    public static final RegistrySupplier<Potion> UNLUCK = register("unluck", "Bad Luck", new MobEffectInstance(MobEffects.UNLUCK, 6000));
    public static final RegistrySupplier<Potion> RESISTANCE = register("resistance", "Resistance", new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800));
    public static final RegistrySupplier<Potion> LONG_RESISTANCE = register("long_resistance", "Resistance", new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600));
    public static final RegistrySupplier<Potion> STRONG_RESISTANCE = register("strong_resistance", "Resistance", new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000, 1));

    public static RegistrySupplier<Potion> register(String name, String translation, MobEffectInstance... instances) {
        RegistrySupplier<Potion> potion = POTIONS.register(location(name), () -> new Potion(name, instances));
        POTION_TRANSLATIONS.put(potion, translation);
        return potion;
    }

    public static void init() { }
}
