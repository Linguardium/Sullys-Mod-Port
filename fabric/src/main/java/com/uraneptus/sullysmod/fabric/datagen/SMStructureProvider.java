package com.uraneptus.sullysmod.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.structures.SnbtToNbt;
import net.minecraft.data.structures.StructureUpdater;

import java.nio.file.Path;
import java.util.List;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

public class SMStructureProvider extends SnbtToNbt {
    public SMStructureProvider(PackOutput packOutput, Path input) {
        super(packOutput, List.of(input));
        //this.addFilter(new StructureUpdater());
    }
}
