package com.uraneptus.sullysmod.core.registry.fabric;

import com.uraneptus.sullysmod.core.registry.SMBlocks;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

public class SMBlocksFabricEvents {
    public static void registerCauldrons() {
        SMBlocks.CAULDRON_MAP.forEach(map -> {
            CauldronFluidContent.registerCauldron(map.cauldronBlock().get(), map.fluid().get(), FluidConstants.BOTTLE, map.levelProperty());
        });
    }
}
