package com.uraneptus.sullysmod.core.other;

import com.google.common.collect.ImmutableMap;
import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.common.blocks.AncientSkullBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class SMTextUtil {
    private static Map<String, String> _TRANSLATABLES = new HashMap<>();

    public static MutableComponent addTranslatable(String translatable, String translation) {
        _TRANSLATABLES.put(translatable, translation);
        if (System.getProperties().contains("fabric-api.datagen")) return Component.translatable(translatable);
        return Component.empty();
    }

    public static MutableComponent addSMTranslatable(String translatable, String translation) {
        return addTranslatable(SullysMod.MOD_ID + "." + translatable, translation);
    }

    public static ImmutableMap<String, String> getTranslatables() {
        return ImmutableMap.copyOf(_TRANSLATABLES);
    }

    public static Pair<Component, Component> addAdvancementTranslatables(String path, String titleTranslation, String descTranslation) {
        var title = addTranslatable(path + ".title", titleTranslation);
        var desc = addTranslatable(path + ".description", descTranslation);
        return Pair.of(title, desc);
    }

    public static String createTranslation(String path) {
        final StringBuilder builder = new StringBuilder();

        for (String part : path.split("_")) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return builder.toString();
    }

    public static String convertSkullTypeToString(AncientSkullBlock.Types type) {
        return type.toString().toLowerCase();
    }
}
