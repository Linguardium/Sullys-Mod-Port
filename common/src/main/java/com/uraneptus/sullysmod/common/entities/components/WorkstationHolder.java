package com.uraneptus.sullysmod.common.entities.components;

import com.uraneptus.sullysmod.common.entities.components.workstations.AbstractWorkstation;
import com.uraneptus.sullysmod.common.entities.components.workstations.Empty;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public interface WorkstationHolder {
    static final String WORKSTATION_NBT_KEY = "SMWorkstation";
    AbstractWorkstation<?> SM$getWorkstation();
    void SM$setWorkstation(AbstractWorkstation<?> workstation);

    default void SM$loadWorkstation(CompoundTag nbt, HolderLookup.Provider registryProvider) {
        if (!nbt.contains(WORKSTATION_NBT_KEY, Tag.TAG_COMPOUND)) {
            this.SM$setWorkstation(Empty.UNIT);
            return;
        }
        AbstractWorkstation<?> workstation = AbstractWorkstation.load(nbt.getCompound(WORKSTATION_NBT_KEY), registryProvider);
        this.SM$setWorkstation(workstation);
    }

    default void SM$saveWorkstation(CompoundTag nbt,HolderLookup.Provider registryProvider) {
        nbt.put(WORKSTATION_NBT_KEY, SM$getWorkstation().save(registryProvider));
    }
}
