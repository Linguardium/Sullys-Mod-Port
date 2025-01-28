package com.uraneptus.sullysmod.core.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.damageTypeKey;

public class SMDamageTypes {
    //public static Map<ResourceKey<DamageType>, DamageType> damageTypeMap = new HashMap<>();

    public static final ResourceKey<DamageType> TORTOISE_SHELL = damageTypeKey("tortoise_shell");
    public static final ResourceKey<DamageType> THROWING_KNIFE = damageTypeKey("throwing_knife");

// Damage types are datagenned now
//    private static ResourceKey<DamageType> register(DamageType damageType) {
//        ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE, location(damageType.msgId()));
//        //damageTypeMap.put(key, damageType);
//        return key;
//    }
}