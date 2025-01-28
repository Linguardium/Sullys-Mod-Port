package com.uraneptus.sullysmod.client.sound;

import com.uraneptus.sullysmod.common.entities.components.WorkstationHolder;
import com.uraneptus.sullysmod.common.entities.components.workstations.Jukebox;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

// TODO: use this
public class FollowJukeboxEntitySoundInstance extends AbstractTickableSoundInstance {
    private final Entity entity;

    public FollowJukeboxEntitySoundInstance(Entity entity, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.RECORDS, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.looping = true;
    }

    @Override
    public void tick() {
        if (
                entity.isRemoved() ||
                !(entity instanceof WorkstationHolder attachable) ||
                !(attachable.SM$getWorkstation() instanceof Jukebox jukebox) ||
                !jukebox.isPlaying()
        ) {
            this.stop();
        }else{
            this.x = entity.getX();
            this.y = entity.getY();
            this.z = entity.getZ();
        }
    }
}
