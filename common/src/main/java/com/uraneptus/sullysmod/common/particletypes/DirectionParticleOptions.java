package com.uraneptus.sullysmod.common.particletypes;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;


public class DirectionParticleOptions implements ParticleOptions {

    public static MapCodec<DirectionParticleOptions> codec(ParticleType<DirectionParticleOptions> particleType) {
        return Direction.CODEC.fieldOf("face").xmap(dir->new DirectionParticleOptions(particleType, dir), DirectionParticleOptions::getFace);
    }

    public static StreamCodec<? super RegistryFriendlyByteBuf, DirectionParticleOptions> streamCodec(ParticleType<DirectionParticleOptions> particleType) {
        return Direction.STREAM_CODEC.map(dir->new DirectionParticleOptions(particleType, dir), DirectionParticleOptions::getFace);
    }

    private final ParticleType<DirectionParticleOptions> type;
    private final Direction face;

    public DirectionParticleOptions(ParticleType<DirectionParticleOptions> particleType, Direction face) {
        this.type = particleType;
        this.face = face;
    }

    @Override
    public @NotNull ParticleType<DirectionParticleOptions> getType() {
        return this.type;
    }

    public Direction getFace() {
        return this.face;
    }

}
