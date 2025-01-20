package com.uraneptus.sullysmod.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class SMRecordItem extends Item {

    public SMRecordItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Item.Properties builder, int lengthInTicks) {
        //super(comparatorValue, soundSupplier, builder, lengthInTicks * 20);
        super(builder);
    }
}
