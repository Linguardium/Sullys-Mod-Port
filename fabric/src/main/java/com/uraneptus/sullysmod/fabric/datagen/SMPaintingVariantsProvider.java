package com.uraneptus.sullysmod.fabric.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.key;
import static com.uraneptus.sullysmod.fabric.datagen.SMTranslationProviderEnUs.PAINTING_TRANSLATIONS;

public class SMPaintingVariantsProvider extends FabricDynamicRegistryProvider {
    public SMPaintingVariantsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.PAINTING_VARIANT));
    }

    @Override
    public String getName() {
        return "Painting Variants";
    }
    public static void bootstrap(BootstrapContext<PaintingVariant> registrar) {
        registerPainting(registrar,"unnerving_night", "RealSpidey", 16, 48);
        registerPainting(registrar,"amber", "Graus", 16, 32);
        registerPainting(registrar,"infestation", "Ninni", 32, 32);
        registerPainting(registrar,"lake", "Farcr", 16, 48);
        registerPainting(registrar,"caverns", "SennaHN", 16, 32);
        registerPainting(registrar,"river_terror", "Graus", 32, 32);
        registerPainting(registrar,"mesmurrizing", "Graus", 32, 32);
        registerPainting(registrar,"jade_dragon", "Shable", 64, 64);
        registerPainting(registrar,"a_visitor", "Ibrokemyribcage", 64, 32);
        registerPainting(registrar,"beginning", "Sully", 16, 48);
        registerPainting(registrar,"home", "Ibrokemyribcage", 64, 64);
        registerPainting(registrar,"illager_beast", "Angery", 64, 48);
        registerPainting(registrar,"thank_you", "Sully", 64, 64);
    }

    private static void registerPainting(BootstrapContext<PaintingVariant> context, String name, String author, int width, int height) {
        ResourceKey<PaintingVariant> key = key(Registries.PAINTING_VARIANT, name);
        PAINTING_TRANSLATIONS.put(key, author);
        PaintingVariant variant = new PaintingVariant(width, height, key.location(), Optional.of(getTranslatable(key, "title").withStyle(ChatFormatting.YELLOW)), Optional.of(getTranslatable(key, "author").withStyle(ChatFormatting.GRAY)));
        context.register(key, variant);
    }
    static MutableComponent getTranslatable(ResourceKey<PaintingVariant> id, String section) {
        return Component.translatable(getTranslatableKey(id, section));
    }
    static String getTranslatableKey(ResourceKey<PaintingVariant> id, String section) {
        return id.location().toLanguageKey("painting", section);
    }

}
