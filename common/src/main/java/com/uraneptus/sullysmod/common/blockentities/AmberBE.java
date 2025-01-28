package com.uraneptus.sullysmod.common.blockentities;

import com.uraneptus.sullysmod.core.registry.SMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.uraneptus.sullysmod.core.other.SMBlockStateProperties.IS_MELTED;
import static net.minecraft.core.component.DataComponents.ENTITY_DATA;

public class AmberBE extends BlockEntity {
    private Entity stuckEntity = null;
//    private CustomData stuckEntityData = CustomData.EMPTY;
//    private boolean entityUpdated = false;
    private EntityType<?> pendingInitialSpawn = null;

    private static final List<String> IGNORED_NBT = Arrays.asList("Leash", "Fire");

    public AmberBE(BlockPos pPos, BlockState pBlockState) {
        super(SMBlockEntityTypes.AMBER.get(), pPos, pBlockState);
    }

    public static void removeIgnoredNBT(CompoundTag pTag) {
        for(String s : IGNORED_NBT) {
            pTag.remove(s);
        }
    }

//    public void setStuckEntityData(CustomData value) {
//        this.stuckEntityData = value;
////        this.renderEntity = value.isEmpty();
//        this.update();
//    }
    public void setStuckEntityData(@Nullable Entity entity) {
        this.stuckEntity = entity;
//        this.renderEntity = value.isEmpty();
        this.update();
    }


    public void clearStuckEntityData() {
        setStuckEntityData(null);
    }
//    public void clearStuckEntityData() {
//        this.stuckEntityData = CustomData.EMPTY;
//    }
    public boolean hasStuckEntity() {
        return stuckEntity != null;
    }
//    public boolean hasStuckEntity() {
//        return !this.stuckEntityData.isEmpty();
//    }


//    public CustomData getEntityStuck() {
//        return this.stuckEntityData;
//    }
    public Optional<Entity> getEntityStuck() {
        return Optional.ofNullable(this.stuckEntity);
    }

    public CustomData saveEntityToCustomData(Entity entity, boolean remove) {
        CompoundTag compoundTag = new CompoundTag();
        if (!entity.save(compoundTag)) return CustomData.EMPTY;
        AmberBE.removeIgnoredNBT(compoundTag);
        if (remove) entity.discard();
        return CustomData.of(compoundTag);
    }

    public void makeEntityStuck(Entity entity) {
        if (this.hasStuckEntity()) return;
        if (!entity.getType().canSerialize()) return;

        Level level = this.getLevel();
        if (level == null) return;

        level.setBlock(this.getBlockPos(), this.getBlockState().setValue(IS_MELTED, false), Block.UPDATE_ALL);

//        CustomData entityData = saveEntityToCustomData(entity, true);
        if (this.hasStuckEntity()) return;
//        if (entityData.isEmpty()) return;
// TODO: Packet handler and EntityCap
//        SMPacketHandler.sendMsg(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new MsgEntityAmberStuck(entity, true));
//        SMEntityCap.getCapOptional(entity).ifPresent(cap -> cap.stuckInAmber = true);
        this.setStuckEntityData(entity);
        entity.discard();

//        this.stuckEntityData = entityData;
//        this.entityUpdated = true;
        this.update();
    }

    public void releaseEntity(Level level, BlockPos pos) {
        if (!this.hasStuckEntity()) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

//        Optional.ofNullable(this.stuckEntityData.parseEntityType(level.registryAccess(), Registries.ENTITY_TYPE))
//        .ifPresent(entityType -> {
        CompoundTag nbt = stuckEntity.saveWithoutId(new CompoundTag());
        removeIgnoredNBT(nbt);
        Entity newEntity = stuckEntity.getType().create(serverLevel, entity->entity.load(nbt), pos, EntitySpawnReason.EVENT, false, false);
        if (newEntity == null) return;
        if (newEntity instanceof ItemEntity) {
            newEntity.setDeltaMovement(0, 0, 0);
        }
        newEntity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        level.addFreshEntity(newEntity);
        this.clearStuckEntityData();
//        SMEntityCap.getCapOptional(entity).ifPresent(cap -> {
//            cap.stuckInAmber = false;
//        });
//        SMPacketHandler.sendMsg(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new MsgEntityAmberStuck(entity, false));
    }

    //This method only stores the entity id and is only used by Amber worldgen
    //The actual saving process for the generated entities is later done in the tick method
    public boolean storeTypeForGeneration(ResourceLocation entityType) {
        if (pendingInitialSpawn != null || this.stuckEntity != null) return false;
        Optional<Holder.Reference<EntityType<?>>> type = BuiltInRegistries.ENTITY_TYPE.get(entityType);
        if (type.isEmpty()) return false;
        this.pendingInitialSpawn = type.get().value();
//        CompoundTag compoundtag = new CompoundTag();
//        compoundtag.putString("id", entityType.toString());
//        this.storeEntity(CustomData.of(compoundtag));
        return true;
    }

    public boolean storeTypeForGeneration(EntityType<?> entityType) {
        if (!entityType.canSerialize()) return false;
        if (pendingInitialSpawn != null || this.stuckEntity != null) return false;
        this.pendingInitialSpawn = entityType;
        return true;
//        return storeTypeForGeneration(EntityType.getKey(entityType));
    }

//    public void storeEntity(CustomData pEntityData) {
//        this.stuckEntityData = pEntityData;// new StuckEntityData(pEntityData);
//    }
    public void storeEntity(Entity entity) {
        this.stuckEntity = entity;// new StuckEntityData(pEntityData);
    }
    public void tick() {
        if (this.pendingInitialSpawn != null && this.getLevel() != null && !this.getLevel().isClientSide()) attemptInitialSpawn();
    }

    private void attemptInitialSpawn() {
        if (this.getLevel() == null) return;
        Entity entity = this.pendingInitialSpawn.create(this.getLevel(), EntitySpawnReason.NATURAL);;
        pendingInitialSpawn = null;
        if (entity == null) return;

        entity.setPos(this.getBlockPos().getCenter());
        entity.setYBodyRot(Mth.randomBetween(level.random, 1, 270));
        this.stuckEntity = entity;
    }

    public void update() {
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.merge(
                DataComponentMap.CODEC.optionalFieldOf("components", DataComponentMap.EMPTY).codec()
                .encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), collectComponents())
                .result().map(element->element instanceof CompoundTag tag?tag:new CompoundTag()).orElse(new CompoundTag())
        );
//        writeEntityData(compoundTag);
    }

//    protected CompoundTag writeEntityData(CompoundTag compoundTag) {
//        compoundTag.put("StuckEntity", this.writeStuckEntity());
//        //compoundTag.putBoolean("RenderEntity", this.renderEntity);
//        return compoundTag;
//    }

//    @Override
//    public void load(CompoundTag pTag) {
//        super.load(pTag);
//        ListTag listtag = pTag.getList("StuckEntity", 10);
//        if (!listtag.isEmpty()) {
//            for(int i = 0; i < listtag.size(); ++i) {
//                CompoundTag compoundtag = listtag.getCompound(i);
//                this.stuckEntityData = new AmberBE.StuckEntityData(compoundtag.getCompound("EntityData"));
//            }
//        } else {
//            this.stuckEntityData = null;
//        }
//        this.renderEntity = pTag.getBoolean("RenderEntity");
//    }
    private Optional<CompoundTag> serializeStuckEntity() {
        return this.getEntityStuck().map(entity-> {
            String id = entity.getEncodeId();
            if (id == null || id.isBlank()) return null;
            CompoundTag tag = entity.saveWithoutId(new CompoundTag());
            tag.putString("id", id);
            return tag;
        }).map(tag->tag.isEmpty()?null:tag);
    }
    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        serializeStuckEntity().ifPresent(entity->{
            builder.set(ENTITY_DATA, CustomData.of(entity));
        });
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
        super.applyImplicitComponents(dataComponentInput);
        deserializeStuckEntity(dataComponentInput.getOrDefault(ENTITY_DATA, CustomData.EMPTY)).ifPresent(entity->{
            this.stuckEntity = entity;
        });
    }
    private Optional<Entity> deserializeStuckEntity(CustomData data) {
        if (data.isEmpty()) return Optional.empty();
        ResourceLocation id = data.parseEntityId();
        if (id == null) return Optional.empty();
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(id).map(Holder.Reference::value).orElse(null);
        if (type == null) return Optional.empty();
        Entity entity = type.create(this.getLevel(), EntitySpawnReason.LOAD);
        if (entity == null) return Optional.empty();
        data.loadInto(entity);
        return Optional.of(entity);
    }
    @Override
    public void setComponents(DataComponentMap dataComponentMap) {
        super.setComponents(dataComponentMap);
        deserializeStuckEntity(dataComponentMap.getOrDefault(ENTITY_DATA, CustomData.EMPTY)).ifPresent(entity->{
            this.stuckEntity = entity;
        });
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveCustomOnly(provider);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static class StuckEntityData {
        final CompoundTag entityData;

        StuckEntityData(CompoundTag pEntityData) {
            AmberBE.removeIgnoredNBT(pEntityData);
            this.entityData = pEntityData;
        }
    }
}
