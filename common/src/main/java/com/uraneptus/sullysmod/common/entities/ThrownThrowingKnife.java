package com.uraneptus.sullysmod.common.entities;

import com.uraneptus.sullysmod.core.registry.SMDamageTypes;
import com.uraneptus.sullysmod.core.registry.SMEntityTypes;
import com.uraneptus.sullysmod.core.registry.SMItems;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import com.uraneptus.sullysmod.mixins.DamageSourcesHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ThrownThrowingKnife extends AbstractArrow {
    private static ItemStack knifeItem = new ItemStack(SMItems.THROWING_KNIFE.get());

    public ThrownThrowingKnife(EntityType<? extends ThrownThrowingKnife> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }

    public ThrownThrowingKnife(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(SMEntityTypes.THROWN_THROWING_KNIFE.get(), pShooter, pLevel, pStack, pStack);
        // Handled by parent class
//        if (pShooter instanceof Player) {
//            this.pickup = AbstractArrow.Pickup.ALLOWED;
//        }
    }

    public ThrownThrowingKnife(Level pLevel, double pX, double pY, double pZ) {
        this(pLevel, pX, pY, pZ, knifeItem.copy());
    }

    public ThrownThrowingKnife(Level pLevel, double pX, double pY, double pZ, ItemStack stack) {
        super(SMEntityTypes.THROWN_THROWING_KNIFE.get(), pX, pY, pZ, pLevel, stack, stack);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return this.knifeItem.copy();
    }

    @Override
    public ItemStack getPickupItem() {
        return super.getPickupItem();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if (entity.getType() == EntityType.ENDERMAN) return;

        float damageAmount = 2.0F;
        if (!entity.onGround() && !entity.isUnderWater()) {
            damageAmount += 4.0F;
        }

        this.playSound(SMSounds.THROWING_KNIFE_HIT.get(), 1.0F, 1.0F);

        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        Entity owner = this.getOwner();
        DamageSource damageSource = ((DamageSourcesHelper)this.damageSources()).createSource(SMDamageTypes.THROWING_KNIFE, this, (owner != null) ? owner : this);

        ItemStack weaponItem = this.getWeaponItem();
        if (weaponItem == null || weaponItem.isEmpty()) weaponItem = this.getPickupItem();

        damageAmount = EnchantmentHelper.modifyDamage(serverLevel, weaponItem, entity, damageSource, damageAmount);
        if (!entity.hurtServer(serverLevel, damageSource, damageAmount)) return;

        EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, this.getWeaponItem());
        if (entity instanceof LivingEntity target) this.doPostHurtEffects(target);

        this.discard();

        // Handled by deflection in parent class

//        } else {
//            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
//            this.setYRot(this.getYRot() + 180.0F);
//            this.yRotO += 180.0F;
//            if (this.level() instanceof ServerLevel serverLevel && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
//                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
//                    this.spawnAtLocation(serverLevel, this.getPickupItem(), 0.1F);
//                }
//
//                this.discard();
//            }
//        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SMSounds.THROWING_KNIFE_HIT_GROUND.get();
    }

    @Override
    protected float getWaterInertia() {
        return 0.7F;
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }
}
