package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.core.other.DatagenDependentHashMap;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.Map;

public class SMPaintingVariants {
    public static Map<String, String> PAINTING_TRANSLATIONS = new DatagenDependentHashMap<>();

    public static final RegistrySupplier<PaintingVariant> UNNERVING_NIGHT = registerPainting("unnerving_night", "RealSpidey", 16, 48);
    public static final RegistrySupplier<PaintingVariant> AMBER = registerPainting("amber", "Graus", 16, 32);
    public static final RegistrySupplier<PaintingVariant> INFESTATION = registerPainting("infestation", "Ninni", 32, 32);
    public static final RegistrySupplier<PaintingVariant> LAKE = registerPainting("lake", "Farcr", 16, 48);
    public static final RegistrySupplier<PaintingVariant> CAVERNS = registerPainting("caverns", "SennaHN", 16, 32);
    public static final RegistrySupplier<PaintingVariant> RIVER_TERROR = registerPainting("river_terror", "Graus", 32, 32);
    public static final RegistrySupplier<PaintingVariant> MESMURRIZING = registerPainting("mesmurrizing", "Graus", 32, 32);
    public static final RegistrySupplier<PaintingVariant> JADE_DRAGON = registerPainting("jade_dragon", "Shable", 64, 64);
    public static final RegistrySupplier<PaintingVariant> A_VISITOR = registerPainting("a_visitor", "Ibrokemyribcage", 64, 32);
    public static final RegistrySupplier<PaintingVariant> BEGINNING = registerPainting("beginning", "Sully", 16, 48);
    public static final RegistrySupplier<PaintingVariant> HOME = registerPainting("home", "Ibrokemyribcage", 64, 64);
    public static final RegistrySupplier<PaintingVariant> ILLAGER_BEAST = registerPainting("illager_beast", "Angery", 64, 48);
    public static final RegistrySupplier<PaintingVariant> THANK_YOU = registerPainting("thank_you", "Sully", 64, 64);

    public static RegistrySupplier<PaintingVariant> registerPainting(String name, String author, int width, int height) {
        PAINTING_TRANSLATIONS.put(name, author);
        return PAINTINGS.register(name, () -> new PaintingVariant(width, height));
    }
}
