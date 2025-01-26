package com.uraneptus.sullysmod.core.registry.neoforge;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SMRegistriesImpl {
    static final Map<ResourceLocation, Registry<?>> registriesToRegister = Maps.newHashMap();
    record RegistryDefinition<T>(ResourceKey<Registry<T>> registryKey, @Nullable ResourceLocation defaultId, boolean synced) {
    }
    public static <T> Registry<T> createRegistry(ResourceLocation registryId, @Nullable ResourceLocation defaultId, boolean synced) {
        if (registriesToRegister.containsKey(registryId)) {
            throw new IllegalArgumentException("Registry " + registryId + " is already registered");
        }
        ResourceKey<Registry<T>> registryKey = ResourceKey.createRegistryKey(registryId);

        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        builder.sync(synced);
        if (defaultId != null) {
            builder.defaultKey(defaultId);
        }
        Registry<T> registry = builder.create();
        registriesToRegister.put(registryId, registry);
        return registry;
    }

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        registriesToRegister.values().forEach(event::register);
        registriesToRegister.clear();
    }

}
