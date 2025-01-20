package com.uraneptus.sullysmod.core.other.tags;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.core.other.SMTagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SMBlockTags {
    //Our Tags
    public static final TagKey<Block> PROJECTILES_BOUNCE_ON = SMTagUtil.blockTag( "projectiles_bounce_on");
    public static final TagKey<Block> PETRIFIED_LOGS = SMTagUtil.blockTag("petrified_logs");

    public static final TagKey<Block> MELTS_AMBER = SMTagUtil.blockTag("melts_amber");

    //Common Tags
    public static final TagKey<Block> WAXABLE_COPPER_BLOCKS = SMTagUtil.blockTag("c", "waxable_copper_blocks");
    public static final TagKey<Block> WAXED_COPPER_BLOCKS = SMTagUtil.blockTag("c", "waxed_copper_blocks");
    public static final TagKey<Block> JADE_ORES = SMTagUtil.blockTag("c", "ores/jade");

}
