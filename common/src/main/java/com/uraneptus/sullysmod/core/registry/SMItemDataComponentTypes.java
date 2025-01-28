package com.uraneptus.sullysmod.core.registry;

import com.mojang.serialization.Codec;
import com.uraneptus.sullysmod.common.components.VenomDataComponent;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.DATA_COMPONENT_TYPES;

public class SMItemDataComponentTypes {
    public static final RegistrySupplier<DataComponentType<VenomDataComponent>> VENOM_DATA_COMPONENT = register("venom", VenomDataComponent.CODEC.codec(), VenomDataComponent.PACKET_CODEC, true);

    private static <T> RegistrySupplier<DataComponentType<T>> register(String name, @Nullable Codec<T> codec, @Nullable StreamCodec<RegistryFriendlyByteBuf, T> packetCodec, boolean cacheEncoding) {
        DataComponentType.Builder<T> builder = DataComponentType.builder();
        if (codec != null) builder.persistent(codec);
        if (packetCodec != null) builder.networkSynchronized(packetCodec);
        if (codec != null && cacheEncoding) builder.cacheEncoding();
        return DATA_COMPONENT_TYPES.register(location(name), builder::build);
    }

    public static void init() { }
}
