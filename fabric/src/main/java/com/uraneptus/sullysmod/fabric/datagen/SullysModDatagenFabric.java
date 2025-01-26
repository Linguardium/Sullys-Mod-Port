package com.uraneptus.sullysmod.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.PackOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SullysModDatagenFabric implements DataGeneratorEntrypoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(SullysModDatagenFabric.class);
    private static final String NBTPathProperty = System.getProperty("structure-input-nbt");
    private static final String SNBTPathProperty = System.getProperty("structure-input-snbt");

    public void registerDatagenProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(SMNeoForgeDataMapProvider::new);
        pack.addProvider(SMTranslationProviderEnUs::new);
        pack.addProvider(SMModelProvider::new);
    }

    public void registerStructureSerializer(FabricDataGenerator.Pack pack) {
        if (NBTPathProperty == null || NBTPathProperty.isBlank()) return;
        if (SNBTPathProperty == null || SNBTPathProperty.isBlank()) return;
        // Read NBT files into SNBT structures. This ensures the nbt structure can be updated for the MC version.
        // Configure property "structure-input-snbt" to the path where the snbt will be located.
        // Currently common/src/datagen/structures/snbt
        // Configure property "structure-input-nbt" to the path where the nbt are located.
        // Currently common/src/datagen/structures/nbt
        Path nbtPath = Path.of(NBTPathProperty);
        Path snbtPath = Path.of(SNBTPathProperty);
        if (Files.notExists(nbtPath)) return;
        try {
            if (Files.notExists(snbtPath)) Files.createDirectories(snbtPath);
            pack.addProvider((FabricDataOutput packOutput) -> new SMStructureSerializationProvider(nbtPath, snbtPath));
        } catch (IOException e) {
            LOGGER.error("Unable create output folder",e);
        }
    }
    public void registerStructureProvider(FabricDataGenerator.Pack pack) {
        if (SNBTPathProperty == null || SNBTPathProperty.isBlank()) return;
        Path snbtPath = Path.of(SNBTPathProperty);
        if (Files.notExists(snbtPath)) return;
        // Read SNBT files into NBT structures. This ensures the nbt structure is up to date for the MC version.
        // Configure property "structure-input-snbt" to the path where the snbt are located.
        // Currently common/src/datagen/structures/snbt
        pack.addProvider((FabricDataOutput packOutput)->
                new SMStructureProvider(
                        new PackOutput(
                                packOutput
                                .getOutputFolder()
                                .resolve("data/sullysmod/structure/")
                        ),
                        snbtPath)
        );
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        registerStructureSerializer(pack);
        registerStructureProvider(pack);
        registerDatagenProviders(pack);
    }

}
