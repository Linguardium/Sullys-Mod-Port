package com.uraneptus.sullysmod.core.other.tags;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.core.other.SMTagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class SMMobEffectTags {

    public static final TagKey<MobEffect> JUNGLE_SPIDER_BENEFICIAL_OR_NEUTRAL_VENOM_EFFECTS = SMTagUtil.mobEffectTag(SullysMod.MOD_ID, "jungle_spider_beneficial_or_neutral_venom_effects");
    public static final TagKey<MobEffect> JUNGLE_SPIDER_HARMFUL_VENOM_EFFECTS = SMTagUtil.mobEffectTag(SullysMod.MOD_ID, "jungle_spider_harmful_venom_effects");
    public static final TagKey<MobEffect> EXTENDED_VENOM_EFFECTS = SMTagUtil.mobEffectTag(SullysMod.MOD_ID, "jungle_spider_extended_venom_effects");

}
