package com.uraneptus.sullysmod;

import com.uraneptus.sullysmod.core.registry.SMSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SullysMod {
    public static final String MOD_ID = "sullysmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        SMSounds.init();
        // Write common init code here.
    }
}
