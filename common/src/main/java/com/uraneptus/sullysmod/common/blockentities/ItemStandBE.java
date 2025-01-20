package com.uraneptus.sullysmod.common.blockentities;

import com.uraneptus.sullysmod.core.registry.SMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ItemStandBE extends BlockEntity {
    ItemStack displayItem = ItemStack.EMPTY;

    public ItemStandBE(BlockPos pPos, BlockState pBlockState) {
        super(SMBlockEntityTypes.ITEM_STAND.get(), pPos, pBlockState);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider provider) {
        super.loadAdditional(pTag, provider);
        this.setDisplayItem(ItemStack.parse(provider, pTag.getCompound("DisplayItem")).orElse(ItemStack.EMPTY));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider provider) {
        super.saveAdditional(pTag, provider);
        if (!this.getDisplayItem().isEmpty()) {
            pTag.put("DisplayItem", this.getDisplayItem().save(provider, new CompoundTag()));
        }

    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveCustomOnly(provider);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ItemStack getDisplayItem() {
        return this.displayItem;
    }

    public void setDisplayItem(ItemStack item) {
        this.displayItem = item;
    }

    @Override
    public BlockEntityType<?> getType() {
        return SMBlockEntityTypes.ITEM_STAND.get();
    }
}
