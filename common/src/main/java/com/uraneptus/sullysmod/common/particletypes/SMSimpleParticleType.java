package com.uraneptus.sullysmod.common.particletypes;

import net.minecraft.core.particles.SimpleParticleType;

public class SMSimpleParticleType extends SimpleParticleType {
    public SMSimpleParticleType() {
        this(false);
    }
    public SMSimpleParticleType(boolean overrideLimiter) {
        super(overrideLimiter);
    }
}
