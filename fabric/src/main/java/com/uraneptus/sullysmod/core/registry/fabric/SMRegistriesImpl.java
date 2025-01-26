package com.uraneptus.sullysmod.core.registry.fabric;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class SMRegistriesImpl {
    public static <T> Registry<T> createRegistry(ResourceLocation registryId, @Nullable ResourceLocation defaultId, boolean synced) {
        FabricRegistryBuilder<T, ? extends MappedRegistry<T>> registryBuilder;
        ResourceKey<Registry<T>> registryKey = ResourceKey.createRegistryKey(registryId);
        if (defaultId != null) {
            registryBuilder = FabricRegistryBuilder.createDefaulted(registryKey, defaultId);
        }else{
            registryBuilder = FabricRegistryBuilder.createSimple(registryKey);
        }
        if (synced) {
            registryBuilder.attribute(RegistryAttribute.SYNCED);
        }
        return registryBuilder.buildAndRegister();
    }
}
