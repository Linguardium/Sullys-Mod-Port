package com.uraneptus.sullysmod.common.blockentities;

import com.uraneptus.sullysmod.common.blocks.utilities.AmberUtil;
import com.uraneptus.sullysmod.core.registry.SMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.minecraft.core.component.DataComponents.ENTITY_DATA;

public class AmberBE extends BlockEntity {
    private CustomData stuckEntityData = CustomData.EMPTY;
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

    public void setStuckEntityData(CustomData value) {
        this.stuckEntityData = value;
//        this.renderEntity = value.isEmpty();
        this.update();
    }
    public void clearStuckEntityData() {
        this.stuckEntityData = CustomData.EMPTY;
    }
    public boolean hasStuckEntity() {
        return !this.stuckEntityData.isEmpty();
    }


    public CustomData getEntityStuck() {
        return this.stuckEntityData;
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

        level.setBlock(this.getBlockPos(), this.getBlockState().setValue(AmberUtil.IS_MELTED, false), Block.UPDATE_ALL);

        CustomData entityData = saveEntityToCustomData(entity, true);
        if (entityData.isEmpty()) return;
// TODO: Packet handler and EntityCap
//        SMPacketHandler.sendMsg(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new MsgEntityAmberStuck(entity, true));
//        SMEntityCap.getCapOptional(entity).ifPresent(cap -> cap.stuckInAmber = true);
        this.stuckEntityData = entityData;
//        this.entityUpdated = true;
        this.update();
    }

    public void releaseEntity(Level level, BlockPos pos) {
        if (this.stuckEntityData.isEmpty()) return;
        if (!(level instanceof ServerLevel serverLevel)) return;
        Optional.ofNullable(this.stuckEntityData.parseEntityType(level.registryAccess(), Registries.ENTITY_TYPE))
        .ifPresent(entityType -> {
            Entity newEntity = entityType.create(serverLevel, entity->this.stuckEntityData.update(entity::load), pos, EntitySpawnReason.EVENT, false, false);
            if (newEntity == null) return;
            if (newEntity instanceof ItemEntity) {
                newEntity.setDeltaMovement(0, 0, 0);
            }
            newEntity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            level.addFreshEntity(newEntity);
        });
        this.clearStuckEntityData();
//        SMEntityCap.getCapOptional(entity).ifPresent(cap -> {
//            cap.stuckInAmber = false;
//        });
//        SMPacketHandler.sendMsg(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new MsgEntityAmberStuck(entity, false));

    }

    //This method only stores the entity id and is only used by Amber worldgen
    //The actual saving process for the generated entities is later done in the tick method
    public boolean storeTypeForGeneration(ResourceLocation entityType) {
        if (!this.stuckEntityData.isEmpty()) return false;
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putString("id", entityType.toString());
        this.storeEntity(CustomData.of(compoundtag));
        return true;
    }

    public boolean storeTypeForGeneration(EntityType<?> entityType) {
        if (!entityType.canSerialize()) return false;
        return storeTypeForGeneration(EntityType.getKey(entityType));
    }

    public void storeEntity(CustomData pEntityData) {
        this.stuckEntityData = pEntityData;// new StuckEntityData(pEntityData);
    }

    public void tick() {
//        CompoundTag stuckEntity = getEntityStuck();
        if (this.pendingInitialSpawn != null || this.level == null) return;
        if (this.level.isClientSide()) return;

        Entity entity = this.pendingInitialSpawn.create(level, EntitySpawnReason.NATURAL);;
        pendingInitialSpawn = null;
        if (entity == null) return;

        entity.setPos(this.getBlockPos().getCenter());
        entity.setYBodyRot(Mth.randomBetween(level.random, 1, 270));
        this.stuckEntityData = saveEntityToCustomData(entity, true);
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

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ENTITY_DATA, this.stuckEntityData);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
        super.applyImplicitComponents(dataComponentInput);
        this.stuckEntityData = dataComponentInput.getOrDefault(ENTITY_DATA, CustomData.EMPTY);
    }

    @Override
    public void setComponents(DataComponentMap dataComponentMap) {
        super.setComponents(dataComponentMap);
        this.stuckEntityData = dataComponentMap.getOrDefault(ENTITY_DATA, CustomData.EMPTY);
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
