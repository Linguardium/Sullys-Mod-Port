package com.uraneptus.sullysmod.neoforge;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.core.registry.neoforge.SMRegistriesImpl;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SullysMod.MOD_ID)
public final class SullysModNeoForge {
    public SullysModNeoForge(IEventBus modBus) {
        // Run our common setup.
        SullysMod.init();
        NeoForge.EVENT_BUS.addListener(SMRegistriesImpl::registerRegistries);
    }

}
