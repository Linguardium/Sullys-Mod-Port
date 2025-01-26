package com.uraneptus.sullysmod.common.entities.components.workstations;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWorkstation<T extends AbstractWorkstation<T>> {
    private static final String WRAPPING_TAG_KEY = "Workstation";
    protected AbstractWorkstation() { }

    public abstract SMWorkstationTypes.WorkstationType<T> getWorkstationType();

    public CompoundTag save(HolderLookup.Provider provider) {
        SMWorkstationTypes.WorkstationType<T> type = this.getWorkstationType();
        Tag tag = SMWorkstationTypes.WORKSTATION_CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, provider),this).ifError(err->
            SullysMod.LOGGER.error("Failed to save data for {}", type.id())
        ).result().orElse(new CompoundTag());
        if (tag instanceof CompoundTag) return (CompoundTag)tag;

        SullysMod.LOGGER.error("Codec for {} did not return a CompoundTag: {}", type.id(), tag.getAsString());

        CompoundTag wrappingTag = new CompoundTag();
        wrappingTag.put(WRAPPING_TAG_KEY, tag);
        return wrappingTag;
    }

    public boolean isEmpty() { return false; }
    public void tick(Entity holder) { }

    @NotNull
    public static AbstractWorkstation<?> load(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag == null) return Empty.UNIT;
        final CompoundTag workstationTag;
        if (tag.contains(WRAPPING_TAG_KEY, Tag.TAG_COMPOUND)) workstationTag = tag.getCompound(WRAPPING_TAG_KEY);
        else workstationTag = tag;
        return SMWorkstationTypes.WORKSTATION_CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, provider), workstationTag).ifError(err->
                SullysMod.LOGGER.error("Failed to load data for workstation: {} ", workstationTag.getAsString())
        ).result().orElse(Empty.UNIT);
    }

    public InteractionResult useWithEmptyHand(Entity holder, LivingEntity user) {
        return InteractionResult.PASS;
    }

    public InteractionResult useWithItem(Entity holder, LivingEntity user, ItemStack stack) {
        return InteractionResult.PASS;
    }
    public InteractionResult attack(Entity holder, LivingEntity user) {
        return InteractionResult.PASS;
    }

    public T getAsType() {
        return (T)this;
    }

}
