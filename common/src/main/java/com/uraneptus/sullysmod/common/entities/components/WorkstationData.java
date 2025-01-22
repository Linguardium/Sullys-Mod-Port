package com.uraneptus.sullysmod.common.entities.components;

import com.uraneptus.sullysmod.core.other.SMItemUtil;
import com.uraneptus.sullysmod.core.other.tags.SMItemTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorkstationData {
    private ItemStack appledWorkstation = ItemStack.EMPTY;
    private ItemStack recordItem = ItemStack.EMPTY;
    JukeboxSongPlayer jukeboxSongPlayer;
    int ticksSinceLastEvent = 0;

    Entity holder;
    public WorkstationData(Entity holder) {
        this.holder = holder;
        // TODO: entity specific jukebox song player
        this.jukeboxSongPlayer = new JukeboxSongPlayer(this::onRecordSongChanged, holder.getOnPos());
    }

    ItemStack getAppliedWorkstation() {
        return appledWorkstation;
    }

    public void setAppliedWorkstation(LevelAccessor level, @Nullable Player player, ItemStack itemStack) {
        if (!this.getAppliedWorkstation().isEmpty() && level instanceof ServerLevelAccessor serverLevel) {
            this.removeAppliedWorkstation(serverLevel, player instanceof ServerPlayer serverPlayer ? serverPlayer : null);
        }
        this.appledWorkstation = itemStack;
    }

    public boolean hasAppliedWorkstation() {
       return !this.getAppliedWorkstation().isEmpty();
    }

    public boolean isCraftingTable() {
        return getAppliedWorkstation().is(SMItemTags.CRAFTING_TABLES);
    }

    public boolean isJukebox() {
        return getAppliedWorkstation().is(SMItemTags.JUKEBOXES);
    }
    public void onRecordSongChanged() {

    }
    public ItemStack getRecordItem() {
        return this.recordItem;
    }
    public void setRecordItem(LevelAccessor level, ItemStack itemStack) {
        this.recordItem = itemStack;
        ticksSinceLastEvent = 0;
        this.jukeboxSongPlayer.stop(level, Blocks.JUKEBOX.defaultBlockState());
        //TODO: handle jukebox player
    }

    public long getRecordTickCount(HolderLookup.Provider provider) {
        ItemStack record = this.getRecordItem();
        if (record.isEmpty() || !record.has(DataComponents.JUKEBOX_PLAYABLE)) return 0;
        return JukeboxSong.fromStack(provider, record).map(song->song.value().lengthInTicks()).orElse(0);
    }

    //void setRecordTickCount(long tickCount);
//    long getRecordStartedTick();
//    void setRecordStartedTick(long startedTick);
    //TODO sync this
//    boolean isRecordPlaying();
//    void setRecordPlaying(boolean isPlaying);
    int getTicksSinceLastEvent() {
        return ticksSinceLastEvent;
    }
    void setTicksSinceLastEvent(int ticksSinceLastEvent) {
        this.ticksSinceLastEvent = ticksSinceLastEvent;
    }

//    static MapCodec<WorkstationAttachable> mapCodec();
//    StreamCodec<RegistryFriendlyByteBuf, ? extends WorkstationAttachable> packetCodec();

    // TODO: Codecify and clean up interaction code
//    default <T> DataResult<T> addSaveData(RegistryOps<T> ops) {
//        return this.mapCodec().codec().encodeStart(ops, this);
//        nbt.put("AppliedWorkstation", this.getAppliedWorkstation().save(provider));
//        nbt.put("RecordItem", this.getRecordItem().save(provider));
//        nbt.putBoolean("IsPlaying", this.isRecordPlaying());
//        nbt.putLong("RecordStartTick", this.getRecordStartedTick());
//        nbt.putLong("TickCount", this.getRecordTickCount());
//    }
//
//    default <T> void readSaveData(Dynamic<T> dynamic) {
//        this.setAppliedWorkstation(ItemStack.parse(provider,nbt.getCompound("AppliedWorkstation")));
//        this.setRecordItem(ItemStack.of(nbt.getCompound("RecordItem")));
//        this.setRecordPlaying(nbt.getBoolean("IsPlaying"));
//        this.setRecordStartedTick(nbt.getLong("RecordStartTick"));
//        this.setRecordTickCount(nbt.getLong("TickCount"));
//    }

    public InteractionResult customInteraction(Player pPlayer, @NotNull InteractionHand pHand) { }

    public void removeRecord(ServerLevelAccessor level, @Nullable ServerPlayer player) {
        if (player != null) player.handleExtraItemsCreatedOnUse(getRecordItem());
        else holder.spawnAtLocation(level.getLevel(), getRecordItem());
        this.setRecordItem(level, ItemStack.EMPTY);
    }

    public void removeAppliedWorkstation(ServerLevelAccessor level, @Nullable ServerPlayer player) {
        if (this.isJukebox()) removeRecord(level, player);
        if (player != null) player.handleExtraItemsCreatedOnUse(getAppliedWorkstation());
        else holder.spawnAtLocation(level.getLevel(), getAppliedWorkstation());
        this.appledWorkstation = ItemStack.EMPTY;
    }
    public InteractionResult applyWorkstation(LevelAccessor level, @Nullable Player player) {
        setAppliedWorkstation(pPlayer.level(), pPlayer, itemInHand.copy());
        entity.refreshDimensions();
        if (!pPlayer.isCreative()) {
            itemInHand.shrink(1);
            return InteractionResult.sidedSuccess(entity.level().isClientSide());
        }
        return InteractionResult.SUCCESS;
    }

    private InteractionResult interactWithAppliedWorkstation(Player pPlayer, @NotNull InteractionHand pHand, Entity entity) {
        if (pPlayer.isShiftKeyDown()) {
            if (itemInHand.is(ItemTags.AXES)) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    removeAppliedWorkstation(serverLevel, pPlayer instanceof ServerPlayer serverPlayer ? serverPlayer : null);
                    entity.refreshDimensions();
                    return InteractionResult.SUCCESS;
                }
                return customInteraction(pPlayer, pHand);
            } else {
                if (isCraftingTable()) {
                    if (! entity.level().isClientSide()) {
                        this.openCraftingMenu((ServerPlayer) pPlayer, entity);
                        pPlayer.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                        return InteractionResult.CONSUME;
                    }
                    return InteractionResult.SUCCESS;
                } else if (isJukebox()) {
                    if (itemInHand.isEmpty()) {
                        if (! getRecordItem().isEmpty()) {
                            if (! entity.level().isClientSide()) {
                                pPlayer.addItem(getRecordItem());
                                setRecordItem(ItemStack.EMPTY);
                                this.setRecordPlaying(false);
                                this.setRecordTickCount(0);
                                this.setTicksSinceLastEvent(0);
                            }
                            return InteractionResult.sidedSuccess(entity.level().isClientSide());
                        }
                    } else if (itemInHand.getItem() instanceof RecordItem recordItem) {
                        if (getRecordItem().isEmpty()) {
                            setRecordItem(recordItem.getDefaultInstance());
                            SMItemUtil.nonCreativeShrinkStack(pPlayer, itemInHand);
                            this.setRecordStartedTick(this.getRecordTickCount());
                            this.setRecordPlaying(true);
                            if (entity.level().isClientSide()) {
                                startRecordPlaying(entity, recordItem);
                            }
                            return InteractionResult.sidedSuccess(entity.level().isClientSide());
                        }
                    }
                    return InteractionResult.sidedSuccess(entity.level().isClientSide());
                }
            }
        }
        return InteractionResult.PASS;
    }

    //It was hell to handle this code for both entities. It works now, and I never want to touch this shit again
    // if the method is to complex/large, split it up - Ling
    public InteractionResult workstationInteraction(Player pPlayer, @NotNull InteractionHand pHand, Entity entity) {
        boolean flag = false;
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (hasAppliedWorkstation()) return interactWithAppliedWorkstation(pPlayer, pHand, entity);
        else if (itemInHand.is(SMItemTags.WORKSTATIONS)) return applyWorkstation(pPlayer.level(), pPlayer);
        else return customInteraction(pPlayer, pHand);
    }

    @OnlyIn(Dist.CLIENT)
    default void startRecordPlaying(Entity entity, RecordItem recordItem) {
        Minecraft mc = Minecraft.getInstance();
        mc.getSoundManager().queueTickingSound(new FollowJukeboxEntitySoundInstance(entity, recordItem.getSound()));
        mc.gui.setNowPlaying(recordItem.getDisplayName());
    }

    default void openCraftingMenu(ServerPlayer player, Entity entity) {
        if (player.containerMenu != player.inventoryMenu) {
            player.closeContainer();
        }
        NetworkHooks.openScreen(player, new SimpleMenuProvider((id, inventory, mPlayer) -> new CraftingMenu(id, inventory, ContainerLevelAccess.create(entity.level(), entity.blockPosition())) {
            @Override
            public boolean stillValid(Player pPlayer) {
                return true;
            }
        }, entity.getName().copy().append(" Crafting")));
    }

    default void handleJukeboxTick(Entity entity, Level level) {
        if (!isJukebox()) return;
        BlockPos pos = entity.blockPosition();
        this.setTicksSinceLastEvent(1 + this.getTicksSinceLastEvent());
        if (!this.getRecordItem().isEmpty() && this.isRecordPlaying()) {
            if (this.getRecordItem().getItem() instanceof RecordItem recorditem) {
                if (this.getRecordTickCount() >= this.getRecordStartedTick() + (long)recorditem.getLengthInTicks() + 20L) {
                    this.setRecordPlaying(false);
                    level.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, pos, GameEvent.Context.of(entity));
                    level.levelEvent(1011, pos, 0);
                } else if (getTicksSinceLastEvent() >= 20) {
                    this.setTicksSinceLastEvent(0);
                    level.gameEvent(GameEvent.JUKEBOX_PLAY, pos, GameEvent.Context.of(entity));
                    if (level instanceof ServerLevel serverlevel) {
                        Vec3 vec3 = Vec3.atBottomCenterOf(pos).add(0.0D, 1.2F, 0.0D);
                        float xOffset = (float)level.getRandom().nextInt(4) / 24.0F;
                        serverlevel.sendParticles(ParticleTypes.NOTE, vec3.x(), vec3.y(), vec3.z(), 0, xOffset, 0.0D, 0.0D, 1.0D);
                    }
                }
            }
        }
        this.setRecordTickCount(1 + getRecordTickCount());
    }

    default void handleServerRemoval(Entity entity) {
        if (this.hasAppliedWorkstation()) {
            entity.spawnAtLocation(new ItemStack(getAppliedWorkstation().getItem()));

            if (!this.getRecordItem().isEmpty()) {
                entity.spawnAtLocation(new ItemStack(getRecordItem().getItem()));
                setRecordItem(ItemStack.EMPTY);
            }
            this.setRecordPlaying(false);
            this.setRecordTickCount(0);
            this.setTicksSinceLastEvent(0);
        }
    }

}
