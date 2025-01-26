package com.uraneptus.sullysmod.common.entities.components.workstations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Jukebox extends AbstractWorkstation<Jukebox> {
    public static final int PLAY_EVENT_INTERVAL_TICKS = 20;

    public static final MapCodec<Jukebox> CODEC = RecordCodecBuilder.mapCodec(instance->instance.group(
            Codec.LONG.fieldOf("TicksSinceSongStarted").forGetter(Jukebox::getTicksSinceSongStarted),
            Codec.LONG.fieldOf("StartedTick").forGetter(Jukebox::getRecordStartedTick),
            JukeboxSong.CODEC.optionalFieldOf("CurrentlyPlayingSong").forGetter(Jukebox::getOptionalPlayingSong),
            ItemStack.CODEC.optionalFieldOf("RecordItem").forGetter(Jukebox::getOptionalRecord)
    ).apply(instance, Jukebox::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Jukebox> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.LONG, Jukebox::getTicksSinceSongStarted,
            ByteBufCodecs.LONG, Jukebox::getRecordStartedTick,
            ByteBufCodecs.optional(JukeboxSong.STREAM_CODEC),Jukebox::getOptionalPlayingSong,
            ByteBufCodecs.optional(ItemStack.STREAM_CODEC),Jukebox::getOptionalRecord,
            Jukebox::new);


    private long ticksSinceSongStarted;
    private long startedTick;
    private final List<JukeboxEventListener> eventListeners = new ArrayList<>();

    @Nullable
    private Holder<JukeboxSong> currentlyPlayingSong;

    ItemStack record = ItemStack.EMPTY;

    public Jukebox(JukeboxEventListener eventListener) {
        this();
        this.addListener(eventListener);
    }

    public Jukebox() { }

    private Jukebox(long ticksSinceSongStarted, long startedTick, Optional<Holder<JukeboxSong>> optionalPlayingSong, Optional<ItemStack> optionalRecord) {
        this.record = optionalRecord.orElse(ItemStack.EMPTY);
        this.setSongDataFromLoad(optionalPlayingSong.orElse(null), ticksSinceSongStarted);
        this.startedTick = startedTick;
    }
    private Optional<ItemStack> getOptionalRecord() {
        ItemStack recordItem = this.getRecordItem();
        if (recordItem.isEmpty()) return Optional.empty();
        return Optional.of(recordItem);
    }
    private Optional<Holder<JukeboxSong>> getOptionalPlayingSong() {
        return Optional.ofNullable(this.currentlyPlayingSong);
    }

    @Override
    public SMWorkstationTypes.WorkstationType<Jukebox> getWorkstationType() {
        return SMWorkstationTypes.JUKEBOX_WORKSTATION_TYPE.get();
    }

    public boolean addListener(JukeboxEventListener eventListener) {
        return this.eventListeners.add(eventListener);
    }

    public boolean removeListener(JukeboxEventListener eventListener) {
        return this.eventListeners.remove(eventListener);
    }

    ItemStack getRecordItem() {
        return record;
    }

    void setRecordItem(ItemStack itemStack, HolderLookup.Provider provider) {
        // todo: handle removal
        record = itemStack;
        setSongDataFromItemStack(itemStack, provider);
    }

    long getRecordStartedTick() {
        return startedTick;
    }

    void setRecordStartedTick(long startedTick) {
        this.startedTick = startedTick;
    }

    public boolean isPlaying() {
        return this.currentlyPlayingSong != null;
    }

    public boolean finishedPlaying() {
        JukeboxSong song = this.getSong();
        if (song == null) return true;
        return song.hasFinished(getTicksSinceSongStarted());
    }

    @Nullable
    public JukeboxSong getSong() {
        return this.currentlyPlayingSong == null ? null : this.currentlyPlayingSong.value();
    }

    private Holder<JukeboxSong> getCurrentlyPlayingSong() {
        return this.currentlyPlayingSong;
    }

    public long getTicksSinceSongStarted() {
        return this.ticksSinceSongStarted;
    }

    void setTicksSinceSongStarted(long tickCount) {
        ticksSinceSongStarted = tickCount;
    }

    public void clearCurrentlyPlayingSong() {
        this.currentlyPlayingSong = null;
        this.ticksSinceSongStarted = 0;
    }

    public void setSongDataFromLoad(@Nullable Holder<JukeboxSong> song, long ticksSinceSongStarted) {
        Optional.ofNullable(song)
            .map(Holder::value)
            .filter(jukeboxSong->!jukeboxSong.hasFinished(ticksSinceSongStarted))
            .ifPresentOrElse(newSong->{
                this.currentlyPlayingSong = song;
                this.ticksSinceSongStarted = ticksSinceSongStarted;
            },this::clearCurrentlyPlayingSong);
    }

    public void setSongDataFromItemStack(ItemStack stack, HolderLookup.Provider provider) {
        Holder<JukeboxSong> song = JukeboxSong.fromStack(provider, stack).orElse(null);
        this.setSongDataFromLoad(song, 0L);
    }

//    public void setSongWithoutPlaying(Holder<JukeboxSong> song, @Nullable Entity holder, BlockPos blockPos, long ticksSinceSongStarted) {
//        if (!song.value().hasFinished(ticksSinceSongStarted)) {
//            this.song = song;
//            this.ticksSinceSongStarted = l;
//        }
//    }
    public void sendEventToListeners(JukeboxWorkstationSongEvent event) {
        this.eventListeners.forEach(eventListener->eventListener.handleJukeboxEvent(this, event));
    }

    public void startCurrentSong(LevelAccessor levelAccessor, @Nullable Entity holder, BlockPos blockPos, long startAtTick) {
        Holder<JukeboxSong> previousSong = this.currentlyPlayingSong;
        JukeboxSong.fromStack(levelAccessor.registryAccess(), this.getRecordItem()).ifPresent(song->{
            setSongDataFromLoad(song, startAtTick);
        });
        if (this.currentlyPlayingSong != previousSong) this.sendEventToListeners(JukeboxWorkstationSongEvent.STARTED);
    }

    public void stop(LevelAccessor levelAccessor, @Nullable Entity holder) {
        if (this.currentlyPlayingSong == null) return;
        this.clearCurrentlyPlayingSong();
        this.sendEventToListeners(JukeboxWorkstationSongEvent.STOPPED);
    }

    public void tick(LevelAccessor levelAccessor, @Nullable Entity holder) {
        if (!this.isPlaying()) return;
        if (this.finishedPlaying()) {
            this.stop(levelAccessor, holder);
            return;
        }

        if (this.shouldEmitJukeboxPlayingEvent() && holder != null) {
            levelAccessor.gameEvent(GameEvent.JUKEBOX_PLAY, holder.blockPosition(), GameEvent.Context.of(holder));
            if (levelAccessor instanceof ServerLevelAccessor serverLevelAccessor) spawnMusicParticles(serverLevelAccessor, holder);
        }

        this.ticksSinceSongStarted++;
    }

    private boolean shouldEmitJukeboxPlayingEvent() {
        return this.ticksSinceSongStarted % PLAY_EVENT_INTERVAL_TICKS == 0L;
    }

    private void spawnMusicParticles(ServerLevelAccessor levelAccessor, @NotNull Entity holder) {
        RandomSource random = levelAccessor.getRandom();
        Vec3 vec3 = holder.position();
        float yVariance = (float)levelAccessor.getRandom().nextInt(4) / 24.0F;
        levelAccessor.getLevel().sendParticles(ParticleTypes.NOTE, vec3.x(), vec3.y(), vec3.z(), 0, yVariance, 0.0, 0.0, 1.0);

    }
//    private static void spawnMusicParticles(LevelAccessor levelAccessor, @Nullable Entity holder, BlockPos blockPos) {
//        if (levelAccessor instanceof ServerLevel serverLevel) {
//
//            Vec3 vec3 = Vec3.atBottomCenterOf(blockPos).add(0.0, 1.2F, 0.0);
//            float f = (float)levelAccessor.getRandom().nextInt(4) / 24.0F;
//            serverLevel.sendParticles(ParticleTypes.NOTE, vec3.x(), vec3.y(), vec3.z(), 0, (double)f, 0.0, 0.0, 1.0);
//        }
//    }

    @FunctionalInterface
    public interface JukeboxEventListener {
        void handleJukeboxEvent(Jukebox jukebox, JukeboxWorkstationSongEvent event);
    }

    public static enum JukeboxWorkstationSongEvent {
        STOPPED,
        STARTED;
    }
}
