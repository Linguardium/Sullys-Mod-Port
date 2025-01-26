package com.uraneptus.sullysmod.fabric.datagen;

import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static com.uraneptus.sullysmod.core.other.SMTextUtil.getWorkstationKey;

public class SMTranslationProviderEnUs extends FabricLanguageProvider {
    protected SMTranslationProviderEnUs(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(getWorkstationKey(SMWorkstationTypes.CRAFTING_TABLE_WORKSTATION_TYPE.get()),"%s Crafting");
        translationBuilder.add(getWorkstationKey(SMWorkstationTypes.JUKEBOX_WORKSTATION_TYPE.get()),"%s Jukebox");
    }

}
