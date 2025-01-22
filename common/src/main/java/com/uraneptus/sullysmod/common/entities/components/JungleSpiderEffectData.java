package com.uraneptus.sullysmod.common.entities.components;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.Nullable;

public record JungleSpiderEffectData(@Nullable Holder<MobEffect> beneficial, @Nullable Holder<MobEffect> harmful) {
    public static final JungleSpiderEffectData EMPTY = new JungleSpiderEffectData(null, null);
    public static MapCodec<JungleSpiderEffectData> CODEC = RecordCodecBuilder.mapCodec(instance->instance.group(
            MobEffect.CODEC.optionalFieldOf("BeneficialEffect", null).forGetter(JungleSpiderEffectData::beneficial),
            MobEffect.CODEC.optionalFieldOf("HarmfulEffect", null).forGetter(JungleSpiderEffectData::harmful)
    ).apply(instance, JungleSpiderEffectData::new));

    public JungleSpiderEffectData withBeneficial(@Nullable Holder<MobEffect> beneficial) {
        return new JungleSpiderEffectData(beneficial, this.harmful());
    }
    public JungleSpiderEffectData withHarmful(@Nullable Holder<MobEffect> harmful) {
        return new JungleSpiderEffectData(harmful, this.beneficial());
    }
}
