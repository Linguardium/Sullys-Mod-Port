package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.common.fluids.WarmingFluidAttributes;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.FLUIDS;

public class SMFluids {
    private static final ResourceLocation stillTexture = location("block/amber");
    private static final ResourceLocation flowingTexture = location("block/flowing_molten_amber");

    public static final ArchitecturyFluidAttributes MOLTEN_AMBER_PROPERTIES = WarmingFluidAttributes.ofSupplier(
                    SMFluids::getFlowingMoltenAmber, SMFluids::getSourceMoltenAmber)
            .slopeFindDistance(2,4)
            .dropOff(2,1)
            .tickDelay(30,10)
            .blockSupplier(()->SMBlocks.MOLTEN_AMBER_BLOCK) // Supplier Supplier due to load order control
            .bucketItemSupplier(()->SMItems.MOLTEN_AMBER_BUCKET) // Supplier Supplier due to load order control
            .temperature(1)
            .viscosity(5) // docs say tbis should be 200 * slopeFindDistance?
            .density(10) // docs say 1000 for water and 3000 for lava?
            .flowingTexture(flowingTexture)
            .sourceTexture(stillTexture)
            .color(0xFF7F03); //1.0F, 0.5F, 0.01F

    public static final RegistrySupplier<FlowingFluid> SOURCE_MOLTEN_AMBER = FLUIDS.register(location("molten_amber_fluid"),
            () -> new ArchitecturyFlowingFluid.Source( SMFluids.MOLTEN_AMBER_PROPERTIES));
    public static final RegistrySupplier<FlowingFluid> FLOWING_MOLTEN_AMBER = FLUIDS.register(location("flowing_molten_amber"),
            () -> new  ArchitecturyFlowingFluid.Flowing(SMFluids.MOLTEN_AMBER_PROPERTIES));

    private static RegistrySupplier<FlowingFluid> getSourceMoltenAmber() {
        return SOURCE_MOLTEN_AMBER;
    }
    private static RegistrySupplier<FlowingFluid> getFlowingMoltenAmber() {
        return FLOWING_MOLTEN_AMBER;
    }

    public static void init() { }
//SMFluidTypes.MOLTEN_AMBER_FLUID_TYPE,

//            FluidType.Properties.create()
//            .motionScale(0.000233D)
//            .pathType(PathType.LAVA)
//            .density(10)
//            .viscosity(5)
//            .supportsBoating(false)
//            .canSwim(false)
//            .canPushEntity(true)
//            .temperature(1));
}
