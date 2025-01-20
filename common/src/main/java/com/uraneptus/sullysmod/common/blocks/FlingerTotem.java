package com.uraneptus.sullysmod.common.blocks;

import com.uraneptus.sullysmod.common.blockentities.FlingerTotemBE;
import com.uraneptus.sullysmod.common.blocks.utilities.SMDirectionalBlock;
import com.uraneptus.sullysmod.core.registry.SMBlockEntityTypes;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.uraneptus.sullysmod.core.other.tags.SMItemTags.HONEYCOMBS;
import static com.uraneptus.sullysmod.core.other.tags.SMItemTags.SHEARS;

public class FlingerTotem extends SMDirectionalBlock implements EntityBlock {
    public static final IntegerProperty HONEY_AMOUNT = IntegerProperty.create("honey_amount", 0, 4);

    public FlingerTotem(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HONEY_AMOUNT, 0));
    }
    // TODO: set up tags for honeycombs and shears
    @Override
    public @NotNull InteractionResult useItemOn(ItemStack itemInHand, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
       if (itemInHand.is(HONEYCOMBS) && blockState.getValue(HONEY_AMOUNT) < 4) {
            increaseHoneyLevel(player, level, blockPos, blockState);
            itemInHand.consume(1, player);
            return InteractionResult.SUCCESS;
        } else if (itemInHand.is(SHEARS) && blockState.getValue(HONEY_AMOUNT) != 0) {
            decreaseHoneyLevel(player, level, blockPos, blockState);
            itemInHand.hurtAndBreak(1,player, interactionHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static void increaseHoneyLevel(@Nullable Entity pEntity, Level pLevel, BlockPos pos, BlockState state) {
        BlockState blockstate = state.setValue(HONEY_AMOUNT, state.getValue(HONEY_AMOUNT) + 1);
        pLevel.setBlock(pos, blockstate, 3);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(pEntity, blockstate));
        pLevel.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SMSounds.FLINGER_ADD_HONEY.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public static void decreaseHoneyLevel(@Nullable Entity pEntity, Level pLevel, BlockPos pos, BlockState state) {
        BlockState blockstate = state.setValue(HONEY_AMOUNT, state.getValue(HONEY_AMOUNT) - 1);
        pLevel.setBlock(pos, blockstate, 3);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(pEntity, blockstate));
        pLevel.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SMSounds.FLINGER_REDUCE_HONEY.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(HONEY_AMOUNT);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SMBlockEntityTypes.FLINGER_TOTEM.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState state, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, SMBlockEntityTypes.FLINGER_TOTEM.get(), FlingerTotemBE::serverTick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
        return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
    }
}