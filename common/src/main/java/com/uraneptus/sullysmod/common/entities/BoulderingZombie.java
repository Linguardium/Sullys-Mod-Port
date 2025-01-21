package com.uraneptus.sullysmod.common.entities;

import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class BoulderingZombie extends Zombie {
    private static final EntityDataAccessor<Boolean> CLIMBING_DATA = SynchedEntityData.defineId(BoulderingZombie.class, EntityDataSerializers.BOOLEAN);
    protected final WallClimberNavigation climberNavigation;
    protected final GroundPathNavigation groundNavigation;
    public final AnimationState climbAnimationState = new AnimationState();

    public BoulderingZombie(EntityType<? extends BoulderingZombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.climberNavigation = new WallClimberNavigation(this, pLevel);
        this.groundNavigation = new GroundPathNavigation(this, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 23.0D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    // TODO: SpawnRestrictions
    public static boolean checkBoulderingZombieSpawnRules(EntityType<? extends BoulderingZombie> entityType, ServerLevelAccessor level, EntitySpawnReason entitySpawnReason, BlockPos pos, RandomSource random) {
        return isInDeepslateLayer(pos, random) && Monster.checkMonsterSpawnRules(entityType, level, entitySpawnReason, pos, random);
    }

    // TODO: programatically handle deepslate level check
    public static boolean isInDeepslateLayer(BlockPos pos, RandomSource random) {
        int chance = random.nextInt(100);
        double y = pos.getY();

        return (y <= -4 && y >= -15 && chance < 50) || (y <= -16 && y >= -30 && chance < 68) || (y <= -31 && y >= -39 && chance < 80) || (y <= -40 && y >= -63 && chance < 90);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CLIMBING_DATA, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (CLIMBING_DATA.equals(pKey)) {
            if (onClimbable()) {
                this.climbAnimationState.start(this.tickCount);
            } else {
                this.climbAnimationState.stop();
            }
        }
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.getTarget() != null) {
                this.navigation = climberNavigation;
                this.setClimbing(this.horizontalCollision);
            } else {
                this.navigation = groundNavigation;
                this.setClimbing(false);
            }
        }
        super.tick();
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypeTags.IS_FIRE)) {
            amount *= 1.5F;
        }
        return super.hurtServer(serverLevel, damageSource, amount);
    }

    @Override
    public boolean onClimbable() {
        return this.entityData.get(CLIMBING_DATA);
    }

    public void setClimbing(boolean pClimbing) {
        this.entityData.set(CLIMBING_DATA, pClimbing);
    }

    @Override
    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SMSounds.BOULDERING_ZOMBIE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SMSounds.BOULDERING_ZOMBIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SMSounds.BOULDERING_ZOMBIE_DEATH.get();
    }
}
