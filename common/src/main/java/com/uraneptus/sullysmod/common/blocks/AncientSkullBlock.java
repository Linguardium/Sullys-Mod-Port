package com.uraneptus.sullysmod.common.blocks;

import com.uraneptus.sullysmod.common.blockentities.AncientSkullBE;
import com.uraneptus.sullysmod.common.blocks.utilities.CustomNoteBlockProvider;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public class AncientSkullBlock extends SkullBlock implements CustomNoteBlockProvider {
    final SoundEvent instrumentSound;
    public AncientSkullBlock(Type pType, RegistrySupplier<SoundEvent> instrumentSound, Properties pProperties) {
        super(pType, pProperties);
        this.instrumentSound = instrumentSound.get();
        AncientSkullBE.SKULLS.add(this);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        if (instrumentSound != null) {
            return new AncientSkullBE(pPos, pState, instrumentSound.location());
        }
        return new AncientSkullBE(pPos, pState);

    }

    public AncientSkullBlock.Types getAncientType() {
        return (Types) this.getType();
    }

    @Override
    public SoundEvent getInstrumentSound() {
        return instrumentSound;
    }

    public enum Types implements SkullBlock.Type, StringRepresentable {
        CRACKED(),
        CRESTED(),
        FLATBILLED(),
        GIGANTIC(),
        HORNED(),
        LONG(),
        TINY(),
        WIDE(),
        RIBBED(),
        UNICORN();

        Types() {
            TYPES.put(getSerializedName(), this);
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
