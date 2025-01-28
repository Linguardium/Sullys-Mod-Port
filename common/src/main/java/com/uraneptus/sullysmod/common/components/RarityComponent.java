package com.uraneptus.sullysmod.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.uraneptus.sullysmod.core.registry.SMItemDataComponentTypes.CUSTOM_RARITY;

public record RarityComponent(int level, String name, @Nullable Style formatting) {
    public static final Codec<RarityComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("level").forGetter(RarityComponent::level),
            Codec.STRING.fieldOf("name").forGetter(RarityComponent::name),
            Style.Serializer.CODEC.optionalFieldOf("style", null).forGetter(RarityComponent::formatting)
    ).apply(instance, RarityComponent::new));

    public static final RarityComponent EMPTY = new RarityComponent(0, "", null);
    public static final StreamCodec<RegistryFriendlyByteBuf, RarityComponent> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, RarityComponent::level,
            ByteBufCodecs.STRING_UTF8, RarityComponent::name,
            ByteBufCodecs.optional(Style.Serializer.TRUSTED_STREAM_CODEC), RarityComponent::getOptionalFormatting,
            (level, name, optFormatting)->new RarityComponent(level, name, optFormatting.orElse(null)));

    private Optional<Style> getOptionalFormatting() {
        return Optional.ofNullable(this.formatting());
    }
    @Nullable public static Style getRarityFormatting(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        RarityComponent component = stack.get(CUSTOM_RARITY.get());
        if (component == null) return null;
        return component.formatting();
    }
}
