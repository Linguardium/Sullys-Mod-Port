package com.uraneptus.sullysmod.common.blocks;

import com.mojang.serialization.MapCodec;
import com.uraneptus.sullysmod.common.blockentities.ItemStandBE;
import com.uraneptus.sullysmod.common.blocks.utilities.SMDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ItemStandBlock extends SMDirectionalBlock implements EntityBlock {
    public static final MapCodec<ItemStandBlock> CODEC = simpleCodec(ItemStandBlock::new);
    public ItemStandBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemInHand, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!(level.getBlockEntity(blockPos) instanceof ItemStandBE itemStand)) return InteractionResult.PASS;
        if (itemStand.getDisplayItem().isEmpty()) {
            if (itemInHand.isEmpty()) return InteractionResult.PASS;
            if (level.isClientSide()) return InteractionResult.SUCCESS;
            itemStand.setDisplayItem(itemInHand.copyWithCount(1));
            itemInHand.consume(1,player);
            return InteractionResult.SUCCESS_SERVER;
        }else {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!(level.getBlockEntity(blockPos) instanceof ItemStandBE itemStand)) return InteractionResult.PASS;
        if (itemStand.getDisplayItem().isEmpty()) return InteractionResult.PASS;

        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (player instanceof ServerPlayer serverPlayer && !player.isCreative()) {
            serverPlayer.handleExtraItemsCreatedOnUse(itemStand.getDisplayItem());
            itemStand.setDisplayItem(ItemStack.EMPTY);
            return InteractionResult.SUCCESS_SERVER;
        }
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState pNewState, boolean pIsMoving) {
        if (!blockState.is(pNewState.getBlock())) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ItemStandBE itemStand && !itemStand.getDisplayItem().isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemStand.getDisplayItem());
            }
            super.onRemove(blockState, level, pos, pNewState, pIsMoving);
        }
    }

    public VoxelShape getVoxelShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.0625, 0.4375, 0.5625, 0.625, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.0625, 0.875), BooleanOp.OR);
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.getVoxelShape();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ItemStandBE(pPos, pState);
    }

}
