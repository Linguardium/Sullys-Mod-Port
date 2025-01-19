package com.uraneptus.sullysmod.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.SnbtToNbt;

import java.nio.file.Path;
import java.util.List;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;

public class SMStructureSerializationProvider extends NbtToSnbt {
    public SMStructureSerializationProvider(Path input, Path output) {
        super(new PackOutput(output), List.of(input));
    }
}
