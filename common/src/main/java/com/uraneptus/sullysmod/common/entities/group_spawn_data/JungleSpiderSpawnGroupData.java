package com.uraneptus.sullysmod.common.entities.group_spawn_data;

import com.uraneptus.sullysmod.common.entities.components.JungleSpiderEffectData;
import com.uraneptus.sullysmod.core.other.tags.SMMobEffectTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Spider;

import java.util.Optional;
import java.util.function.Predicate;

public class JungleSpiderSpawnGroupData extends Spider.SpiderEffectsGroupData {
    public final JungleSpiderEffectData effectData;
    private JungleSpiderSpawnGroupData(JungleSpiderEffectData data) {
        this.effectData = data;
    }

    public static JungleSpiderSpawnGroupData generate(RandomSource random) {
        Optional<Holder<MobEffect>> beneficial = chooseBeneficialEffect(random, effect->true);
        Predicate<Holder<MobEffect>> isCompatible = beneficial.map(JungleSpiderSpawnGroupData::compatibleWithPredicate).orElse(effect->true);

        Optional<Holder<MobEffect>> harmful = chooseHarmfulEffect(random, isCompatible);
        return new JungleSpiderSpawnGroupData(new JungleSpiderEffectData(beneficial.orElse(null), harmful.orElse(null)));
    }

    private static Predicate<Holder<MobEffect>> compatibleWithPredicate(Holder<MobEffect> effect1) {
        return effect2->
                effectCompatibleWith(effect1,effect2) && effectCompatibleWith(effect2, effect1);
    }

    private static boolean effectCompatibleWith(Holder<MobEffect> effect1, Holder<MobEffect> effect2) {
        if (effect1 == MobEffects.NIGHT_VISION) return effect2 != MobEffects.BLINDNESS;
        if (effect1 == MobEffects.DIG_SLOWDOWN) return effect2 != MobEffects.DIG_SPEED;
        if (effect1 == MobEffects.MOVEMENT_SLOWDOWN) return effect2 != MobEffects.MOVEMENT_SPEED;
        return true;
    }

    private static Optional<Holder<MobEffect>> chooseBeneficialEffect(RandomSource random, Predicate<Holder<MobEffect>> filter) {
        return chooseEffectFromTag(SMMobEffectTags.JUNGLE_SPIDER_BENEFICIAL_OR_NEUTRAL_VENOM_EFFECTS, filter, random);
    }

    private static Optional<Holder<MobEffect>> chooseHarmfulEffect(RandomSource random, Predicate<Holder<MobEffect>> filter) {
        return chooseEffectFromTag(SMMobEffectTags.JUNGLE_SPIDER_HARMFUL_VENOM_EFFECTS, filter, random);
    }

    private static Optional<Holder<MobEffect>> chooseEffectFromTag(TagKey<MobEffect> tag, Predicate<Holder<MobEffect>> filter, RandomSource random) {
        return BuiltInRegistries.MOB_EFFECT.get(tag).flatMap(list->
                Util.getRandomSafe(list.stream().filter(filter).toList(), random)
        );
    }

}
