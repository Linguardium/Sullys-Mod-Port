package com.uraneptus.sullysmod.common.blockentities;

import com.uraneptus.sullysmod.core.registry.SMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class AncientSkullBE extends SkullBlockEntity {
    public static HashSet<Block> SKULLS = new HashSet<>();

    public AncientSkullBE(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    public AncientSkullBE(BlockPos pPos, BlockState pBlockState, ResourceLocation customInstrument) {
        super(pPos, pBlockState);
        DataComponentMap.Builder builder = DataComponentMap.builder();
        this.collectImplicitComponents(builder);
        builder.set(DataComponents.NOTE_BLOCK_SOUND, customInstrument);
        DataComponentMap datacomponentmap = builder.build();
        this.applyImplicitComponents(new BlockEntity.DataComponentInput() {
            @Nullable
            @Override
            public <T> T get(DataComponentType<T> dataComponentType) {
                return datacomponentmap.get(dataComponentType);
            }
            @Override
            public <T> T getOrDefault(DataComponentType<? extends T> dataComponentType, T object) {
                return datacomponentmap.getOrDefault(dataComponentType, (T)object);
            }
        });
    }

    @Override
    public BlockEntityType<?> getType() {
        return SMBlockEntityTypes.ANCIENT_SKULL.get();
    }

}
