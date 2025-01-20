package com.uraneptus.sullysmod;

import com.uraneptus.sullysmod.core.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SullysMod {
    public static final String MOD_ID = "sullysmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        SMRegistries.init();
        SMBlocksetTypes.init();
        SMBlockEntityTypes.init();
        SMSounds.init();
        SMBlocks.init();
        SMFluids.init();

        SMBlocks.registerCauldronBlocks();
        // Write common init code here.
    }
}
