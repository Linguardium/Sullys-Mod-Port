package com.uraneptus.sullysmod.common.blocks;

import com.uraneptus.sullysmod.common.blocks.utilities.AmberUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SolidAmberBlock extends Block {
    public SolidAmberBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AmberUtil.IS_MELTED, false));
    }

    @Override
    public void animateTick(BlockState state, Level pLevel, BlockPos pos, RandomSource pRandom) {
        AmberUtil.spawnAmberParticles(state, pLevel, pos, pRandom);
    }

    @Override
    public void tick(BlockState state, ServerLevel pLevel, BlockPos pos, RandomSource pRandom) {
        AmberUtil.fillCauldronBehavior(state, pLevel, pos);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter pLevel, BlockPos pos, CollisionContext pContext) {
        return AmberUtil.basicCollisionShapeUpdate(super.getCollisionShape(state, pLevel, pos, pContext), state, pLevel, pos, pContext);
    }

    @Override
    public void entityInside(BlockState state, Level pLevel, BlockPos pos, Entity pEntity) {
        AmberUtil.basicEntityInsideBehavior(this, state, pLevel, pos, pEntity);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        super.stepOn(level, blockPos, blockState, entity);
       BlockState state = AmberUtil.getStateForMelting(level, blockPos,blockState);
       if (state != blockState) level.setBlockAndUpdate(blockPos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AmberUtil.IS_MELTED);
    }
}
