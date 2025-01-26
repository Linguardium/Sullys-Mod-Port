package com.uraneptus.sullysmod.core.registry;

import com.uraneptus.sullysmod.core.SMFeatures;
import com.uraneptus.sullysmod.core.other.SMTextDefinitions;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.CreativeModeTab;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;
import static com.uraneptus.sullysmod.core.registry.SMRegistries.CREATIVE_MODE_TABS;


public class SMCreativeModeTabs {

    public static final RegistrySupplier<CreativeModeTab> ARTIFACT_TAB = CREATIVE_MODE_TABS.register(location("artifact_tab"), () ->
        CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 4)
                        .title(SMTextDefinitions.ARTIFACT_TAB_TITLE)
                        .icon(() -> SMItems.BROKEN_VASE.get().getDefaultInstance())
                        .displayItems((parameters, output) -> SMItems.ARTIFACT_DESC_MAP.forEach((item, desc) -> {
                            if (SMFeatures.isEnabled(SMFeatures.ARTIFACTS)) {
                                if (item.get() == SMBlocks.UNICORN_ANCIENT_SKULL.getFirst().get().asItem()) {
                                    if (Platform.isModLoaded("sullysmod_additions") && Platform.isModLoaded("twilightforest")) {
                                        output.accept(item.get());
                                    }
                                } else {
                                    output.accept(item.get());
                                }
                            }
                        }))
                        .build()

    );
}
