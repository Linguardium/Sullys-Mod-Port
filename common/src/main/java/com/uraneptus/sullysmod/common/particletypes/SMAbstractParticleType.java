package com.uraneptus.sullysmod.common.particletypes;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class SMAbstractParticleType<T extends ParticleOptions> extends ParticleType<T> {
    final MapCodec<T> codec;
    final StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;

    public SMAbstractParticleType(Function<ParticleType<T>, MapCodec<T>> mapcodecFactory, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> packetCodecFactory) {
        super(false); // overrideLimiter false
        codec = mapcodecFactory.apply(this);
        streamCodec = packetCodecFactory.apply(this);
    }

    @Override
    public @NotNull MapCodec<T> codec() {
        return codec;
    }

    @Override
    public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
        return streamCodec;
    }


}
