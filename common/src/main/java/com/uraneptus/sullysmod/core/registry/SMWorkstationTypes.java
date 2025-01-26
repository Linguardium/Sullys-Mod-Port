package com.uraneptus.sullysmod.core.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.uraneptus.sullysmod.common.entities.components.workstations.AbstractWorkstation;
import com.uraneptus.sullysmod.common.entities.components.workstations.CraftingTable;
import com.uraneptus.sullysmod.common.entities.components.workstations.Empty;
import com.uraneptus.sullysmod.common.entities.components.workstations.Jukebox;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class SMWorkstationTypes {

    public static final RegistrySupplier<WorkstationType<Empty>> EMPTY_WORKSTATION_TYPE = register( "empty", MapCodec.unit(Empty.UNIT), StreamCodec.unit(Empty.UNIT));
    public static final RegistrySupplier<WorkstationType<Jukebox>> JUKEBOX_WORKSTATION_TYPE = register( "jukebox", Jukebox.CODEC, Jukebox.PACKET_CODEC);
    public static final RegistrySupplier<WorkstationType<CraftingTable>> CRAFTING_TABLE_WORKSTATION_TYPE = register("crafting_table", CraftingTable.CODEC, CraftingTable.PACKET_CODEC);
    public static final Codec<AbstractWorkstation<?>> WORKSTATION_CODEC = SMRegistries.WORKSTATION_TYPE_REGISTRY.byNameCodec().dispatch("WorkstationType", AbstractWorkstation::getWorkstationType, WorkstationType::codec);

    public record WorkstationType<T extends AbstractWorkstation<T>>(ResourceLocation id, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) { }
    private static <T extends AbstractWorkstation<T>> RegistrySupplier<WorkstationType<T>> register(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
        ResourceLocation id = location(name);
        return SMRegistries.WORKSTATION_TYPES.register(id, ()->new WorkstationType<>(id, codec, packetCodec));
    }
}
