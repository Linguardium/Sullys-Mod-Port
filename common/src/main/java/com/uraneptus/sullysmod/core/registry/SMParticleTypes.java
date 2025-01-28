package com.uraneptus.sullysmod.core.registry;

import com.mojang.serialization.MapCodec;
import com.uraneptus.sullysmod.common.particletypes.DirectionParticleOptions;
import com.uraneptus.sullysmod.common.particletypes.SMAbstractParticleType;
import com.uraneptus.sullysmod.common.particletypes.SMSimpleParticleType;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.PARTICLE_TYPES;

public class SMParticleTypes {

    public static final RegistrySupplier<ParticleType<DirectionParticleOptions>> RICOCHET = register("ricochet", DirectionParticleOptions::codec, DirectionParticleOptions::streamCodec);

    public static final RegistrySupplier<SimpleParticleType> BLOT_EYES = register("blot_eyes");
    public static final RegistrySupplier<SimpleParticleType> AMBER_DRIPPING = register("amber_drip");
    public static final RegistrySupplier<SimpleParticleType> AMBER_FALL = register("amber_fall");
    public static final RegistrySupplier<SimpleParticleType> AMBER_LAND = register("amber_land");

    private static RegistrySupplier<SimpleParticleType> register(final String name) {
        return PARTICLE_TYPES.register(location(name), SMSimpleParticleType::new);
    }

    private static <T extends ParticleOptions> RegistrySupplier<ParticleType<T>> register(
            String idPath,
            Function<ParticleType<T>, MapCodec<T>> mapcodecFactory,
            Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> packetCodecFactory
    ) {
        ResourceLocation id = location(idPath);
        return PARTICLE_TYPES.register(id, ()-> new SMAbstractParticleType<T>(mapcodecFactory, packetCodecFactory));
    }

    public static void init() { }
}
