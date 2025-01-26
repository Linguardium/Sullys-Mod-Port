package com.uraneptus.sullysmod.core.registry;

import com.google.common.base.Suppliers;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static com.uraneptus.sullysmod.SullysMod.MOD_ID;
import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class SMRegistries {

    // Modded registries
    public static final ResourceLocation WORKSTATION_TYPE_REGISTRY_ID = location("workstation_type");
    public static final ResourceKey<Registry<SMWorkstationTypes.WorkstationType<?>>> WORKSTATION_TYPE_KEY = ResourceKey.createRegistryKey(WORKSTATION_TYPE_REGISTRY_ID);
    public static final Registry<SMWorkstationTypes.WorkstationType<?>> WORKSTATION_TYPE_REGISTRY = createRegistry(WORKSTATION_TYPE_REGISTRY_ID, null, false);

    // Registry registrars (like deferred registries)
    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    public static final Registrar<Item> ITEMS = MANAGER.get().get(Registries.ITEM);
    public static final Registrar<Block> BLOCKS = MANAGER.get().get(Registries.BLOCK);
    public static final Registrar<SoundEvent> SOUNDS = MANAGER.get().get(Registries.SOUND_EVENT);
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = MANAGER.get().get(Registries.BLOCK_ENTITY_TYPE);
    public static final Registrar<Fluid> FLUIDS = MANAGER.get().get(Registries.FLUID);
    public static final Registrar<Feature<?>> FEATURES = MANAGER.get().get(Registries.FEATURE);
    public static final Registrar<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = MANAGER.get().get(Registries.TREE_DECORATOR_TYPE);
    public static final Registrar<EntityType<?>> ENTITY_TYPES = MANAGER.get().get(Registries.ENTITY_TYPE);
    public static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = MANAGER.get().get(Registries.RECIPE_SERIALIZER);
    public static final Registrar<RecipeType<?>> RECIPE_TYPES = MANAGER.get().get(Registries.RECIPE_TYPE);
    public static final Registrar<LootItemConditionType> LOOT_CONDITION_TYPES = MANAGER.get().get(Registries.LOOT_CONDITION_TYPE);
    public static final Registrar<ParticleType<?>> PARTICLE_TYPES = MANAGER.get().get(Registries.PARTICLE_TYPE);
    public static final Registrar<DataComponentType<?>> DATA_COMPONENT_TYPES = MANAGER.get().get(Registries.DATA_COMPONENT_TYPE);
    public static final Registrar<CreativeModeTab> CREATIVE_MODE_TABS = MANAGER.get().get(Registries.CREATIVE_MODE_TAB);

    public static final Registrar<SMWorkstationTypes.WorkstationType<?>> WORKSTATION_TYPES = MANAGER.get().get(WORKSTATION_TYPE_KEY);

    public static void init() { }

    @ExpectPlatform
    public static <T> Registry<T> createRegistry(ResourceLocation registryId, @Nullable ResourceLocation defaultId, boolean synced) {
        throw new UnsupportedOperationException("Platform expected");
    }

}
