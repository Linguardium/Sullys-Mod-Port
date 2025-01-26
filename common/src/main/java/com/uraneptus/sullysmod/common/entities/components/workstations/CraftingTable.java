package com.uraneptus.sullysmod.common.entities.components.workstations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.uraneptus.sullysmod.core.other.SMTextUtil;
import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class CraftingTable extends AbstractWorkstation<CraftingTable> {
    public static final MapCodec<CraftingTable> CODEC = RecordCodecBuilder.mapCodec(instance->instance.group(
            Codec.BOOL.fieldOf("boolean").forGetter(cr->true)
    ).apply(instance, CraftingTable::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CraftingTable> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, cr->true,
            CraftingTable::new);

    public CraftingTable() {}
    private CraftingTable(boolean bool) {
        this();
    }

    @Override
    public SMWorkstationTypes.WorkstationType<CraftingTable> getWorkstationType() {
        return SMWorkstationTypes.CRAFTING_TABLE_WORKSTATION_TYPE.get();
    }


    @Override
    public InteractionResult useWithEmptyHand(Entity holder, LivingEntity user) {
        if (user instanceof Player player) player.openMenu(createMenuProvider(holder));
        return InteractionResult.SUCCESS;
    }

    private MenuProvider createMenuProvider(Entity holder) {
        Component name = Component.translatable(SMTextUtil.getWorkstationKey(getWorkstationType()), holder.getName());
        return new SimpleMenuProvider((syncId, inventory, player)->
                new CustomCraftingMenu(
                        syncId,
                        inventory,
                        ContainerLevelAccess.create(holder.level(), holder.blockPosition())),
                name);
    }

    private static class CustomCraftingMenu extends CraftingMenu {

        public CustomCraftingMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
            super(i, inventory, containerLevelAccess);
        }

        @Override
        public boolean stillValid(Player pPlayer) {
            return true;
        }
    }


}
