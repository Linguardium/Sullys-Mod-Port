package com.uraneptus.sullysmod.common.entities;

public interface WorkstationAttachable {
//    ItemStack getAppliedWorkstationItem();
//    void setAppliedWorkstation(ItemStack itemStack);
//    boolean hasAppliedWorkstation();
//
//    default boolean isCraftingTable() {
//        return getAppliedWorkstationItem().is(SMItemTags.CRAFTING_TABLES);
//    }
//    default boolean isJukebox() {
//        return getAppliedWorkstationItem().is(SMItemTags.JUKEBOXES);
//    }
//
//    //TODO sync this
//    boolean isRecordPlaying();
//    void setRecordPlaying(boolean isPlaying);
//    int getTicksSinceLastEvent();
//    void setTicksSinceLastEvent(int ticksSinceLastEvent);
//
//    Vec3 position();
//
//    default Vec3 getWorkstationOffset() {
//        return this.position().add(0,1.2d,0);
//    }
//
////    static MapCodec<WorkstationAttachable> mapCodec();
////    StreamCodec<RegistryFriendlyByteBuf, ? extends WorkstationAttachable> packetCodec();
//
//    // TODO: Codecify and clean up interaction code
////    default <T> DataResult<T> addSaveData(RegistryOps<T> ops) {
////        return this.mapCodec().codec().encodeStart(ops, this);
////        nbt.put("AppliedWorkstation", this.getAppliedWorkstation().save(provider));
////        nbt.put("RecordItem", this.getRecordItem().save(provider));
////        nbt.putBoolean("IsPlaying", this.isRecordPlaying());
////        nbt.putLong("RecordStartTick", this.getRecordStartedTick());
////        nbt.putLong("TickCount", this.getRecordTickCount());
////    }
//
////    default <T> void readSaveData(Dynamic<T> dynamic) {
////        this.setAppliedWorkstation(ItemStack.parse(provider,nbt.getCompound("AppliedWorkstation")));
////        this.setRecordItem(ItemStack.of(nbt.getCompound("RecordItem")));
////        this.setRecordPlaying(nbt.getBoolean("IsPlaying"));
////        this.setRecordStartedTick(nbt.getLong("RecordStartTick"));
////        this.setRecordTickCount(nbt.getLong("TickCount"));
////    }
//
//    InteractionResult customInteraction(Player pPlayer, @NotNull InteractionHand pHand);
//
//    //It was hell to handle this code for both entities. It works now, and I never want to touch this shit again
//    default InteractionResult workstationInteraction(Player pPlayer, @NotNull InteractionHand pHand, Entity entity) {
//        boolean flag = false;
//        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
//        if (hasAppliedWorkstation()) {
//            if (pPlayer.isShiftKeyDown()) {
//                if (itemInHand.is(ItemTags.AXES)) {
//                    if (isJukebox() && !getRecordItem().isEmpty()) {
//                        if (!entity.level().isClientSide()) {
//                            pPlayer.addItem(getRecordItem());
//                            setRecordItem(ItemStack.EMPTY);
//                            this.setRecordPlaying(false);
//                            this.setRecordTickCount(0);
//                            this.setTicksSinceLastEvent(0);
//                        }
//                    }
//                    SMItemUtil.nonCreativeAddItems(pPlayer, new ItemStack(this.getAppliedWorkstation().getItem()));
//                    setAppliedWorkstation(ItemStack.EMPTY);
//                    entity.refreshDimensions();
//                    return InteractionResult.sidedSuccess(entity.level().isClientSide());
//                }
//                flag = true;
//            } else {
//                if (isCraftingTable()) {
//                    if (!entity.level().isClientSide()) {
//                        this.openCraftingMenu((ServerPlayer)pPlayer, entity);
//                        pPlayer.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
//                        return InteractionResult.CONSUME;
//                    }
//                    return InteractionResult.SUCCESS;
//                } else if (isJukebox()) {
//                    if (itemInHand.isEmpty()) {
//                        if (!getRecordItem().isEmpty()) {
//                            if (!entity.level().isClientSide()) {
//                                pPlayer.addItem(getRecordItem());
//                                setRecordItem(ItemStack.EMPTY);
//                                this.setRecordPlaying(false);
//                                this.setRecordTickCount(0);
//                                this.setTicksSinceLastEvent(0);
//                            }
//                            return InteractionResult.sidedSuccess(entity.level().isClientSide());
//                        }
//                    } else if (itemInHand.getItem() instanceof RecordItem recordItem) {
//                        if (getRecordItem().isEmpty()) {
//                            setRecordItem(recordItem.getDefaultInstance());
//                            SMItemUtil.nonCreativeShrinkStack(pPlayer, itemInHand);
//                            this.setRecordStartedTick(this.getRecordTickCount());
//                            this.setRecordPlaying(true);
//                            if (entity.level().isClientSide()) {
//                                startRecordPlaying(entity, recordItem);
//                            }
//                            return InteractionResult.sidedSuccess(entity.level().isClientSide());
//                        }
//                    }
//                    return InteractionResult.sidedSuccess(entity.level().isClientSide());
//                }
//            }
//        } else if (itemInHand.is(SMItemTags.CRAFTING_TABLES) || itemInHand.is(SMItemTags.JUKEBOXES)) {
//            setAppliedWorkstation(itemInHand.copy());
//            entity.refreshDimensions();
//            if (!pPlayer.isCreative()) {
//                itemInHand.shrink(1);
//                return InteractionResult.sidedSuccess(entity.level().isClientSide());
//            }
//            return InteractionResult.SUCCESS;
//        } else {
//            flag = true;
//        }
//
//        if (flag) {
//            return customInteraction(pPlayer, pHand);
//        }
//
//        return InteractionResult.PASS;
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    default void startRecordPlaying(Entity entity, ItemStack recordItem) {
//        Minecraft mc = Minecraft.getInstance();
//        mc.getSoundManager().queueTickingSound(new FollowJukeboxEntitySoundInstance(entity, recordItem.getSound()));
//        mc.gui.setNowPlaying(recordItem.getDisplayName());
//    }
//
//    default void openCraftingMenu(ServerPlayer player, Entity entity) {
//        if (player.containerMenu != player.inventoryMenu) {
//            player.closeContainer();
//        }
//        NetworkHooks.openScreen(player, new SimpleMenuProvider((id, inventory, mPlayer) -> new CraftingMenu(id, inventory, ContainerLevelAccess.create(entity.level(), entity.blockPosition())) {
//            @Override
//            public boolean stillValid(Player pPlayer) {
//                return true;
//            }
//        }, entity.getName().copy().append(" Crafting")));
//    }
//
//    default void handleJukeboxTick(Entity entity, Level level) {
//        if (!isJukebox()) return;
//        BlockPos pos = entity.blockPosition();
//        this.setTicksSinceLastEvent(1 + this.getTicksSinceLastEvent());
//        if (!this.getRecordItem().isEmpty() && this.isRecordPlaying()) {
//            if (this.getRecordItem().getItem() instanceof RecordItem recorditem) {
//                if (this.getRecordTickCount() >= this.getRecordStartedTick() + (long)recorditem.getLengthInTicks() + 20L) {
//                    this.setRecordPlaying(false);
//                    level.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, pos, GameEvent.Context.of(entity));
//                    level.levelEvent(1011, pos, 0);
//                } else if (getTicksSinceLastEvent() >= 20) {
//                    this.setTicksSinceLastEvent(0);
//                    level.gameEvent(GameEvent.JUKEBOX_PLAY, pos, GameEvent.Context.of(entity));
//                    if (level instanceof ServerLevel serverlevel) {
//                        Vec3 vec3 = Vec3.atBottomCenterOf(pos).add(0.0D, 1.2F, 0.0D);
//                        float xOffset = (float)level.getRandom().nextInt(4) / 24.0F;
//                        serverlevel.sendParticles(ParticleTypes.NOTE, vec3.x(), vec3.y(), vec3.z(), 0, xOffset, 0.0D, 0.0D, 1.0D);
//                    }
//                }
//            }
//        }
//        this.setRecordTickCount(1 + getRecordTickCount());
//    }
//
//    default void handleServerRemoval(Entity entity) {
//        if (this.hasAppliedWorkstation()) {
//            entity.spawnAtLocation(new ItemStack(getAppliedWorkstation().getItem()));
//
//            if (!this.getRecordItem().isEmpty()) {
//                entity.spawnAtLocation(new ItemStack(getRecordItem().getItem()));
//                setRecordItem(ItemStack.EMPTY);
//            }
//            this.setRecordPlaying(false);
//            this.setRecordTickCount(0);
//            this.setTicksSinceLastEvent(0);
//        }
//    }
}