package com.uraneptus.sullysmod.common.entities;

import com.uraneptus.sullysmod.common.entities.components.WorkstationHolder;
import com.uraneptus.sullysmod.common.entities.components.workstations.AbstractWorkstation;
import com.uraneptus.sullysmod.common.entities.components.workstations.Empty;
import com.uraneptus.sullysmod.core.other.tags.SMBlockTags;
import com.uraneptus.sullysmod.core.registry.SMDamageTypes;
import com.uraneptus.sullysmod.core.registry.SMItems;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import com.uraneptus.sullysmod.mixins.DamageSourcesHelper;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TortoiseShell extends Entity implements OwnableEntity, WorkstationHolder {
    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Integer> SPIN_TICKS = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.INT);
//    private static final EntityDataAccessor<ItemStack> WORKSTATION = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.ITEM_STACK);
//    private static final EntityDataAccessor<ItemStack> RECORD_ITEM = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.ITEM_STACK);
//    private static final EntityDataAccessor<Boolean> IS_RECORD_PLAYING = SynchedEntityData.defineId(TortoiseShell.class, EntityDataSerializers.BOOLEAN);
//    long recordTickCount;
//    long recordStartedTick;
//    int ticksSinceLastEvent;
    private AbstractWorkstation<?> workstation = Empty.UNIT;
    public TortoiseShell(EntityType<? extends TortoiseShell> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }
    public AbstractWorkstation<?> workstation() {
        return workstation;
    }

    public void setOwner(LivingEntity pOwner) {
        this.setOwnerUUID(pOwner.getUUID());
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID pUuid) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(pUuid));
    }

    // TODO: Entity Dimensions
//    @Override
//    protected float getEyeHeight(Pose pPose, EntityDimensions pSize) {
//        return pSize.height;
//    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        float additionalHeight = !this.SM$getWorkstation().isEmpty() ? 0.25F : 0.0F;
        return super.getDimensions(pPose).scale(1.0F, 1.0F + additionalHeight);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ID_HURT, 0);
        builder.define(DATA_ID_HURTDIR, 1);
        builder.define(DATA_ID_DAMAGE, 0.0F);
        builder.define(SPIN_TICKS, 0);
        builder.define(DATA_OWNERUUID_ID, Optional.empty());
//        builder.define(WORKSTATION, ItemStack.EMPTY);
//        builder.define(RECORD_ITEM, ItemStack.EMPTY);
//        builder.define(IS_RECORD_PLAYING, false);
    }

    public Integer getSpinTicksEntityData() {
        return this.entityData.get(SPIN_TICKS);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    //This prevents the entity from moving when the player is sprinting and hits the entity
    @Override
    public void push(double pX, double pY, double pZ) {}

    @Override
    public Vec3 getRelativePortalPosition(Direction.Axis pAxis, BlockUtil.FoundRectangle pPortal) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(pAxis, pPortal));
    }

    public void setSpinTimer() {
        this.entityData.set(SPIN_TICKS, 22);
    }
//
//    @Override
//    public InteractionResult customInteraction(Player player, InteractionHand hand) {
//        double yLookAnglePlayer = player.getLookAngle().get(Direction.Axis.Y);
//        double y = this.getDeltaMovement().get(Direction.Axis.Y);
//        double x = this.getX() - player.getX();
//        double z = this.getZ() - player.getZ();
//        if (y == -0.0 && !this.isUnderWater() && (yLookAnglePlayer > -0.6D && yLookAnglePlayer < 0.1) && getSpinTicksEntityData() == 0) {
//            double d2 = Math.max(x * x + z * z, 0.001D);
//            this.setDeltaMovement(x / d2 * 2.1D, 0.05D, z / d2 * 2.1D);
//            setSpinTimer();
//            this.setOwner(player);
//            this.setYRot(player.getYRot());
//            this.yRotO = this.getYRot();
//            return InteractionResult.SUCCESS;
//        }
//
//        return InteractionResult.PASS;
//    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        return this.SM$getWorkstation().useWithEmptyHand(this, pPlayer);
//        return this.workstationInteraction(pPlayer, pHand, this);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource pSource, float pAmount) {
        if (pSource == this.damageSources().cactus() || pSource == this.damageSources().onFire() || pSource == this.damageSources().inFire()) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                this.spawnAtLocation(level, this.getDropItem());
            }
            this.remove(RemovalReason.DISCARDED);
            return true;
        }

        if (pSource == this.damageSources().lava()) {
            this.remove(RemovalReason.KILLED);
            return true;
        }

        if (this.isInvulnerableToBase(pSource)) return false;
        if (this.isRemoved()) return true;

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + pAmount * 10.0F);
        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, pSource.getEntity());
        if ((pSource.getEntity() instanceof Player player && player.isCreative()) || this.getDamage() > 40.0F) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                this.spawnAtLocation(level, this.getDropItem());
            }
            this.discard();
        }
        return true;
    }

    public Item getDropItem() {
        return SMItems.TORTOISE_SHELL.get();
    }

    @Override
    public void animateHurt(float pYaw) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    private void hurtEntity(List<Entity> pEntities) {
        for (Entity entity : pEntities) {
            if (entity instanceof LivingEntity livingEntity) {
                LivingEntity owner = this.getOwner();
                if (livingEntity instanceof Player player) {
                    if (player.isBlocking()) {
                        player.getCooldowns().addCooldown(player.getUseItem(), 100);
                        player.stopUsingItem();
                        player.level().broadcastEntityEvent(player, EntityEvent.SHIELD_DISABLED);
                    }
                    if (!player.isBlocking()) {
                        handleDamage(owner, entity);
                    }
                } else {
                    handleDamage(owner, entity);
                }
            }
            if (entity instanceof Ravager ravagerEntity) {
                if (ravagerEntity.getStunnedTick() == 0 && ravagerEntity.getRoarTick() == 0) {
                    ravagerEntity.handleEntityEvent((byte) 39);
                    ravagerEntity.playSound(SoundEvents.RAVAGER_STUNNED, 1.0F, 1.0F);
                    ravagerEntity.level().broadcastEntityEvent(ravagerEntity, EntityEvent.RAVAGER_STUNNED);
                }
            }
        }
    }

    public void handleDamage(@Nullable LivingEntity owner, Entity entity) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        DamageSource source = ((DamageSourcesHelper)this.damageSources()).createSource(SMDamageTypes.TORTOISE_SHELL, this, owner == null ? this : owner);
        entity.hurtServer(serverLevel, source, 4);
        if (owner != null) {
            owner.setLastHurtMob(entity);
        }
    }

    private void hitShield(List<Entity> pEntities) {
        for (Entity entity : pEntities) {
            if (entity instanceof Player player) {
                if (player.isBlocking()) {
                    double x = player.getX() - this.getX();
                    double z = player.getZ() - this.getZ();
                    double shellX = this.getX() - player.getX();
                    double shellZ = this.getZ() - player.getZ();
                    double d2 = Math.max(x * x + z * z, 0.001D);

                    this.setDeltaMovement(shellX / d2 * 0.4D, 0.005D, shellZ / d2 * 0.4D);

                    player.getCooldowns().addCooldown(player.getUseItem(), 100);
                    player.stopUsingItem();
                    player.level().broadcastEntityEvent(player, EntityEvent.SHIELD_DISABLED);
                }
            }
        }
    }

    private void knockBack(List<Entity> pEntities) {
        for (Entity entity : pEntities) {
            if (entity instanceof LivingEntity) {
                double x = entity.getX() - this.getX();
                double z = entity.getZ() - this.getZ();
                double shellX = this.getX() - entity.getX();
                double shellZ = this.getZ() - entity.getZ();
                double d2 = Math.max(x * x + z * z, 0.001D);

                this.setDeltaMovement(shellX / d2 * 0.4D, 0.005D, shellZ / d2 * 0.4D);
                entity.push(x / d2 * 0.05D, 0.005D, z / d2 * 0.05D);
            }
        }
    }


    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize().add(this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy)).scale((double)pVelocity);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    protected static BlockHitResult getTortoiseShellPOVHitResult(Level pLevel, Entity entity, ClipContext.Fluid pFluidMode) {
        float xRot = entity.getXRot();
        float yRot = entity.getYRot();
        Vec3 vec3 = entity.getEyePosition();
        float f2 = Mth.cos(-yRot * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-yRot * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-xRot * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-xRot * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double range = 1.15;
        Vec3 vec31 = vec3.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, pFluidMode, entity));
    }

    private void blockKnockBack() {
        Vec3 vec3 = this.getDeltaMovement();
        BlockHitResult hitResult = getTortoiseShellPOVHitResult(this.level(), this, ClipContext.Fluid.NONE);
        BlockPos blockPos = hitResult.getBlockPos();

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            if (hitResult.getDirection().getAxis() == Direction.Axis.X) {
                this.shoot(this.getDeltaMovement().reverse().x, vec3.y, vec3.z, 0.80F, 0F);
                this.setYRot(this.getYRot() + 180);
                this.yRotO = this.getYRot();
            }
            if (hitResult.getDirection().getAxis() == Direction.Axis.Z) {
                this.shoot(vec3.x, vec3.y, this.getDeltaMovement().reverse().z, 0.80F, 0F);
            }
            if (this.level().getBlockState(blockPos).is(SMBlockTags.PROJECTILES_BOUNCE_ON)) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SMSounds.JADE_RICOCHET.get(), this.getSoundSource(), 1.0F, 0.0F);
                this.entityData.set(SPIN_TICKS, Mth.clamp(getSpinTicksEntityData() + 20, 0, 30));
            }
        }
    }

    @Override
    public void tick() {
        Level level = this.level();
        if (this.getSpinTicksEntityData() > 0) {
            this.hitShield(level.getEntities(this, this.getBoundingBox().inflate(0.50D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            this.hurtEntity(level.getEntities(this, this.getBoundingBox().inflate(0.10D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            this.knockBack(level.getEntities(this, this.getBoundingBox().inflate(0.10D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            blockKnockBack();
            this.entityData.set(SPIN_TICKS, this.getSpinTicksEntityData() - 1);
            while (this.getDeltaMovement().x() < -0.7 || this.getDeltaMovement().z() < -0.7) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 0.6D, 0.6D));
            }
            while (this.getDeltaMovement().x() > 0.7 || this.getDeltaMovement().z() > 0.7) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 0.6D, 0.6D));
            }
        }
        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        super.tick();
        this.SM$getWorkstation().tick(this);

        if (!this.isNoGravity()) {
            double yVelocity = -0.04D;
// TODO: fluid slowing?
//
//            if (this.wasEyeInWater) {
//                yVelocity *= this.getFluidMotionScale(fluidType);
//            }

            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, yVelocity, 0.0D));
        }


//        BlockPos bottomPosition = this.getBlockPosBelowThatAffectsMyMovement();
//        float friction = this.onGround() ? level.getBlockState(bottomPosition).getBlock().getFriction() * 1.55F : 1.55F;
//        float defaultFriction = this.level().getBlockState(bottomPosition).getBlock().getFriction();

//        double y = this.getDeltaMovement().get(Direction.Axis.Y);
//        if (y == -0.04 && !this.isInFluidType() && defaultFriction == 0.6F) {
//            this.setDeltaMovement(this.getDeltaMovement().multiply(friction, 0.98D, friction));
//        }
//        if (this.isInFluidType()) {
//            this.setDeltaMovement(this.getDeltaMovement().multiply(0.85, 1, 0.85));
//        }


        if (this.getDeltaMovement() != Vec3.ZERO) {
            this.move(MoverType.SELF, this.getDeltaMovement());
        }

        if (this.isInLava()) {
            this.lavaHurt();
            this.fallDistance *= 0.5F;
        }
    }

//    @Override
//    public void remove(Entity.RemovalReason pReason) {
//        super.remove(pReason);
//        this.handleServerRemoval(this);
//    }

//    @Override
//    public void onClientRemoval() {
//        if (this.hasAppliedWorkstation() && !this.getRecordItem().isEmpty()) {
//
//        }
//        super.onClientRemoval();
//    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("spinTicks", getSpinTicksEntityData());
        if (this.getOwnerUUID() != null) {
            pCompound.putUUID("Owner", this.getOwnerUUID());
        }
        this.SM$saveWorkstation(pCompound, this.level().registryAccess());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        UUID uuid;
        if (pCompound.hasUUID("Owner")) {
            uuid = pCompound.getUUID("Owner");
        } else {
            String s = pCompound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }
        this.SM$loadWorkstation(pCompound, this.level().registryAccess());
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }
    public void setDamage(float pDamageTaken) {
        this.entityData.set(DATA_ID_DAMAGE, pDamageTaken);
    }
    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }
    public void setHurtTime(int pHurtTime) {
        this.entityData.set(DATA_ID_HURT, pHurtTime);
    }
    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }
    public void setHurtDir(int pHurtDirection) {
        this.entityData.set(DATA_ID_HURTDIR, pHurtDirection);
    }
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }

    @Override
    public AbstractWorkstation<?> SM$getWorkstation() {
        return workstation;
    }

    @Override
    public void SM$setWorkstation(AbstractWorkstation<?> workstation) {
        this.workstation = workstation;
    }
//    @Override
//    public ItemStack getAppliedWorkstation() {
//        return this.entityData.get(WORKSTATION);
//    }
//    @Override
//    public void setAppliedWorkstation(ItemStack itemStack) {
//        this.entityData.set(WORKSTATION, itemStack);
//    }
//    @Override
//    public boolean hasAppliedWorkstation() {
//        return !getAppliedWorkstation().isEmpty();
//    }
//    @Override
//    public ItemStack getRecordItem() {
//        return this.entityData.get(RECORD_ITEM);
//    }
//    @Override
//    public void setRecordItem(ItemStack itemStack) {
//        this.entityData.set(RECORD_ITEM, itemStack);
//    }
//    @Override
//    public long getRecordTickCount() {
//        return this.tickCount;
//    }
//    @Override
//    public void setRecordTickCount(long tickCount) {
//        this.recordTickCount = tickCount;
//    }
//    @Override
//    public long getRecordStartedTick() {
//        return this.recordStartedTick;
//    }
//    @Override
//    public void setRecordStartedTick(long startedTick) {
//        this.recordStartedTick = startedTick;
//    }
//    @Override
//    public boolean isRecordPlaying() {
//        return this.entityData.get(IS_RECORD_PLAYING);
//    }
//    @Override
//    public void setRecordPlaying(boolean isPlaying) {
//        this.entityData.set(IS_RECORD_PLAYING, isPlaying);
//    }
//    @Override
//    public int getTicksSinceLastEvent() {
//        return this.ticksSinceLastEvent;
//    }
//    @Override
//    public void setTicksSinceLastEvent(int ticksSinceLastEvent) {
//        this.ticksSinceLastEvent = ticksSinceLastEvent;
//    }
}