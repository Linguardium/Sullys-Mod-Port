package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.entities.TortoiseShell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public class SMDispenseBehaviors {

    public static void register() {
        DispenserBlock.registerBehavior(SMItems.TORTOISE_SHELL.get(), new ShellDispenseItemBehavior());

        DispenserBlock.registerProjectileBehavior(SMItems.THROWING_KNIFE.get());

// Handled by Equippable component now
//
//        SMBlocks.ANCIENT_SKULLS.forEach(block -> DispenserBlock.registerBehavior(block.get().asItem(), new OptionalDispenseItemBehavior() {
//            @Override
//            protected ItemStack execute(BlockSource pSource, ItemStack pStack) {
//                this.setSuccess(ArmorItem.dispenseArmor(pSource, pStack));
//                return pStack;
//            }
//        }));
    }

    private static class ShellDispenseItemBehavior extends OptionalDispenseItemBehavior {
        @Override
        protected @NotNull ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            this.setSuccess(false);
            Level level = blockSource.level();
            Direction direction = blockSource.state().getValueOrElse(DispenserBlock.FACING, Direction.NORTH);
            BlockPos blockPos = blockSource.pos().relative(direction);

            if (!level.getBlockState(blockPos).isAir()) return itemStack;

            TortoiseShell shell = SMEntityTypes.TORTOISE_SHELL.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);
            if (shell == null) return itemStack;

            shell.moveTo(blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F , 0F, 0.0F);
            shell.shoot(direction.getStepX(), (float)direction.getStepY() + 0.1F, direction.getStepZ(), 0.65F, 0F);
            level.playSound(null, blockSource.pos(), SMSounds.TORTOISE_SHELL_PLACE.get(), SoundSource.BLOCKS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            shell.setSpinTimer();
            if (!level.addFreshEntity(shell)) return itemStack;
            this.setSuccess(true);
            itemStack.shrink(1);
            return itemStack;
        }
    }
}
