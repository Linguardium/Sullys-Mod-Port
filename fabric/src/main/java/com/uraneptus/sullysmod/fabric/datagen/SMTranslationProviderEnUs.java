package com.uraneptus.sullysmod.fabric.datagen;

import com.uraneptus.sullysmod.core.other.SMTextUtil;
import com.uraneptus.sullysmod.core.registry.SMBlocks;
import com.uraneptus.sullysmod.core.registry.SMItems;
import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.BlockItem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.uraneptus.sullysmod.core.other.SMTextUtil.getWorkstationKey;

public class SMTranslationProviderEnUs extends FabricLanguageProvider {

    public static Map<ResourceKey<PaintingVariant>, String> PAINTING_TRANSLATIONS = new HashMap<>();

    protected SMTranslationProviderEnUs(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(getWorkstationKey(SMWorkstationTypes.CRAFTING_TABLE_WORKSTATION_TYPE.get()),"%s Crafting");
        translationBuilder.add(getWorkstationKey(SMWorkstationTypes.JUKEBOX_WORKSTATION_TYPE.get()),"%s Jukebox");
        PAINTING_TRANSLATIONS.forEach((name, author)->{
            translationBuilder.add(SMPaintingVariantsProvider.getTranslatableKey(name,"name"), SMTextUtil.createTranslation(name.location().getPath()));
            translationBuilder.add(SMPaintingVariantsProvider.getTranslatableKey(name,"author"), author);
        });
        SMBlocks.AUTO_TRANSLATE.forEach(block->translationBuilder.add(block.get(), SMTextUtil.createTranslation(block.getId().getPath())));
        SMItems.AUTO_TRANSLATE.stream().filter(i->!(i.get() instanceof BlockItem)).forEach(item->translationBuilder.add(item.get(), SMTextUtil.createTranslation(item.getId().getPath())));

    }

}
