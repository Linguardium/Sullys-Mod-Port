package com.uraneptus.sullysmod.common.blocks.utilities;

import com.google.common.base.Suppliers;
import com.uraneptus.sullysmod.common.blocks.AmberLayeredCauldronBlock;
import com.uraneptus.sullysmod.core.other.tags.SMBlockTags;
import com.uraneptus.sullysmod.core.registry.SMBlocks;
import com.uraneptus.sullysmod.core.registry.SMFluids;
import com.uraneptus.sullysmod.core.registry.SMItems;
import com.uraneptus.sullysmod.core.registry.SMParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.uraneptus.sullysmod.core.other.SMBlockStateProperties.IS_MELTED;
import static com.uraneptus.sullysmod.mixins.PointedDripstoneDrippingLogic.findFillableCauldronBelow;
import static net.minecraft.core.cauldron.CauldronInteraction.emptyBucket;
import static net.minecraft.core.cauldron.CauldronInteraction.fillBucket;

public class AmberUtil {
    public static final VoxelShape MELTING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.0F, 1.0D);
    public static final Predicate<BlockState> AMBER_MELTING_BLOCKS = (blockstate) -> blockstate.is(SMBlockTags.MELTS_AMBER) && blockstate.getLightEmission() >= 3;

    // this is used during a getCollisionShape which may not be called on serverside.
    public static VoxelShape basicCollisionShapeUpdate(VoxelShape parentShape, BlockState blockState, BlockGetter pLevel, BlockPos blockPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();
            if (entity instanceof Projectile) return parentShape;
            if (entity == null)  return parentShape;
            Level level = entity.level();

            boolean shouldMeltFlag = false;
            //level.setBlock(pos, state.setValue(IS_MELTED, false), Block.UPDATE_ALL);
            if (blockState.getOptionalValue(IS_MELTED).orElse(false)) return Shapes.empty();
        }
        return parentShape;
    }
    public static BlockState getStateForMelting(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide()) return blockState;
        if (!blockState.hasProperty(IS_MELTED)) return blockState;
        BlockState meltedBlockState = blockState.setValue(IS_MELTED, true);

        boolean shouldMelt = BlockPos.betweenClosedStream(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))
                .anyMatch(pos-> {
                    BlockState neighborState = level.getBlockState(pos);
                    return AmberUtil.AMBER_MELTING_BLOCKS.test(neighborState) ||
                            (neighborState.getOptionalValue(IS_MELTED).orElse(false) && level.getBrightness(LightLayer.BLOCK, pos.above()) >= 9);
                });
        if (shouldMelt) return meltedBlockState;
        return blockState;
    }
    // Basic functionality for this method appears to have been copied from PowderSnowBlock#entityInside
    public static void basicEntityInsideBehavior(Block instance, BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!state.hasProperty(IS_MELTED)) return;
        BlockState blockState = getStateForMelting(level, pos, state);
        if (state != blockState) level.setBlockAndUpdate(pos, blockState);
        if (!blockState.getValue(IS_MELTED)) return;
        if ((entity instanceof LivingEntity) && !entity.getBlockStateOn().is(blockState.getBlock())) return;

        Vec3 stuckSpeedMultiplier = new Vec3(0.8f, 0.1f, 0.8f);
        if (!(entity instanceof Player && entity.isVehicle())) {
            stuckSpeedMultiplier = new Vec3(0.5,0.1,0.5);
        }
        entity.makeStuckInBlock(blockState, stuckSpeedMultiplier);

        trySpawnEntityMovementParticles(level, entity, pos, blockState, level.getRandom());
    }
    public static void trySpawnEntityMovementParticles(Level level, Entity entity, BlockPos blockPos, BlockState state, RandomSource random) {
        if (level.isClientSide()) {
            boolean moved = !entity.oldPosition().equals(entity.position());
            if (moved && random.nextBoolean()) {
                BlockParticleOption particle = new BlockParticleOption(ParticleTypes.BLOCK, state);
                float velocityX = Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336f;
                float velocityY = 0.5f;
                float velocityZ = Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336f;
                level.addParticle(particle, entity.getX(), blockPos.getY() + 1, entity.getZ(), velocityX, velocityY, velocityZ);
            }
        }

    }

    // Deviates enough from vanilla to warrant its own method for now. May move to dripstone logic
    // anyway.
    public static void fillCauldronBehavior(BlockState amberState, ServerLevel level, BlockPos blockPos) {
        if (!amberState.hasProperty(IS_MELTED) || !amberState.getValue(IS_MELTED)) return;

        BlockPos cauldronPos = findFillableCauldronBelow(level, blockPos, SMFluids.SOURCE_MOLTEN_AMBER.get());
        if (cauldronPos == null) return;

        BlockState cauldronState = level.getBlockState(cauldronPos);
        if (!(cauldronState.getBlock() instanceof AbstractCauldronBlock cauldronBlock)) return;
        if (cauldronBlock.isFull(cauldronState)) return;

        // TODO: handle open fluid storages?
        if (cauldronState.is(Blocks.CAULDRON)) {
            cauldronState = SMBlocks.AMBER_CAULDRON.get().defaultBlockState();
        } else if (cauldronState.getBlock() instanceof AmberLayeredCauldronBlock) {
            int fluidLevel = cauldronState.getValue(BlockStateProperties.LEVEL_CAULDRON) + 1;
            cauldronState = cauldronState.setValue(BlockStateProperties.LEVEL_CAULDRON, fluidLevel);
        }
        level.setBlockAndUpdate(cauldronPos, cauldronState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, cauldronPos, GameEvent.Context.of(cauldronState));
        level.levelEvent(LevelEvent.SOUND_DRIP_WATER_INTO_CAULDRON, cauldronPos, 0);
    }

// Dripping mechanics deviate from vanilla
// Appears that it does not need a stalactite to drip into cauldron
// copied from PointedDripstoneBlock
// removed and deferred to vanilla logic via invoker
//
//    @Nullable
//    private static BlockPos findFillableCauldronBelow(Level pLevel, BlockPos pos) {
//        Predicate<BlockState> predicate = state -> state.is(Blocks.CAULDRON) || state.is(SMBlocks.AMBER_CAULDRON.get());
//        BiPredicate<BlockPos, BlockState> bipredicate = (p_202034_, p_202035_) -> canDripThrough(pLevel, p_202034_, p_202035_);
//        return findBlockVertical(pLevel, pos, Direction.DOWN.getAxisDirection(), bipredicate, predicate, 11).orElse(null);
//    }
//
// copied from PointedDripstoneBlock
// removed and deferred to vanilla logic via invoker
//
//    private static boolean canDripThrough(BlockGetter pLevel, BlockPos pos, BlockState state) {
//        if (state.isAir()) {
//            return true;
//        } else if (state.isSolidRender(pLevel, pos)) {
//            return false;
//        } else if (!state.getFluidState().isEmpty()) {
//            return false;
//        } else {
//            VoxelShape voxelshape = state.getCollisionShape(pLevel, pos);
//            return !Shapes.joinIsNotEmpty(Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D), voxelshape, BooleanOp.AND);
//        }
//    }

// copied from PointedDripstoneBlock
// removed and deferred to vanilla logic via invoker
//
//    private static Optional<BlockPos> findBlockVertical(LevelAccessor pLevel, BlockPos pos, Direction.AxisDirection pAxis, BiPredicate<BlockPos, BlockState> positionalStatePredicate, Predicate<BlockState> statePredicate, int pMaxIterations) {
//        Direction direction = Direction.get(pAxis, Direction.Axis.Y);
//        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
//
//        for(int i = 1; i < pMaxIterations; ++i) {
//            blockpos$mutableblockpos.move(direction);
//            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
//            if (statePredicate.test(blockstate)) {
//                return Optional.of(blockpos$mutableblockpos.immutable());
//            }
//
//            if (pLevel.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !positionalStatePredicate.test(blockpos$mutableblockpos, blockstate)) {
//                return Optional.empty();
//            }
//        }
//
//        return Optional.empty();
//    }

    public static void spawnAmberParticles(BlockState state, Level pLevel, BlockPos pos, RandomSource pRandom) {
        if (state.getValue(IS_MELTED)) {
            for(int i = 0; i < pRandom.nextInt(1) + 1; ++i) {
                if (pRandom.nextInt(3) == 0) {
                    spawnParticlesOnBlockFaces(pLevel, pos, SMParticleTypes.AMBER_DRIPPING.get(), UniformInt.of(0, 1));
                }
            }
        }
    }

    private static void spawnParticlesOnBlockFaces(Level pLevel, BlockPos pos, ParticleOptions pParticle, IntProvider pCount) {
        for(Direction direction : Direction.values()) {
            if (direction != Direction.UP) {
                if (pLevel.getBlockState(pos.relative(direction)).isAir()) {
                    ParticleUtils.spawnParticlesOnBlockFace(pLevel, pos, pParticle, pCount, direction, () -> new Vec3(Mth.nextDouble(pLevel.random, -0.5D, 0.5D), Mth.nextDouble(pLevel.random, -0.5D, 0.5D), Mth.nextDouble(pLevel.random, -0.5D, 0.5D)), 0.55D);
                }
            }
        }
    }

    public static Supplier<CauldronInteraction.InteractionMap> AMBER_CAULDRON_INTERACTION = Suppliers.memoize(()->{
        CauldronInteraction.InteractionMap interactionMap = CauldronInteraction.newInteractionMap("amber");
        interactionMap.map().put(
                Items.BUCKET,
                (blockState, level, blockPos, player, interactionHand, itemStack) -> fillBucket(
                        blockState,
                        level,
                        blockPos,
                        player,
                        interactionHand,
                        itemStack,
                        new ItemStack(SMItems.MOLTEN_AMBER_BUCKET.get()),
                        AmberUtil::isAmberCauldronAndFull,
                        SoundEvents.BUCKET_FILL
                )
        );
        return interactionMap;
    });

    public static boolean isAmberCauldronAndFull(BlockState state) {
        if (state.getBlock() instanceof AmberLayeredCauldronBlock amberCauldronBlock) {
            return amberCauldronBlock.isFull(state);
        }
        return true;
    }

    public static void registerEmptyAmberBucketIntoCauldronBehavior() {
        CauldronInteraction.EMPTY.map().put(SMItems.MOLTEN_AMBER_BUCKET.get(), (blockState, level, blockPos, player, interactionHand, itemStack) ->
                emptyBucket(level, blockPos, player, interactionHand, itemStack, SMBlocks.AMBER_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY)
        );

    }
}
