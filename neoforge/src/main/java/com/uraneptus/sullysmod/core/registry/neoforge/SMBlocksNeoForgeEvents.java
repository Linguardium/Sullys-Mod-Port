package com.uraneptus.sullysmod.core.registry.neoforge;

import com.uraneptus.sullysmod.core.registry.SMBlocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class SMBlocksNeoForgeEvents {

    @SubscribeEvent
    public static void registerCauldrons(RegisterCauldronFluidContentEvent event) {
        SMBlocks.CAULDRON_MAP.forEach(map->{
            event.register(map.cauldronBlock().get(), map.fluid().get(), FluidType.BUCKET_VOLUME, map.levelProperty());
        });
    }
}
