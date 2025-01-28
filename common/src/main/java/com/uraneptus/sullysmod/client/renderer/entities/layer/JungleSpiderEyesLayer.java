package com.uraneptus.sullysmod.client.renderer.entities.layer;

import com.uraneptus.sullysmod.client.model.JungleSpiderModel;
import com.uraneptus.sullysmod.client.renderer.entities.SMTextures;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.JungleSpiderRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import org.jetbrains.annotations.NotNull;


public class JungleSpiderEyesLayer extends EyesLayer<JungleSpiderRenderState, JungleSpiderModel> {
    private static final RenderType JUNGLE_SPIDER_EYES = RenderType.eyes(SMTextures.JUNGLE_SPIDER_EYES);
    public JungleSpiderEyesLayer(RenderLayerParent<JungleSpiderRenderState, JungleSpiderModel> parent) {
        super(parent);
    }

    @Override
    public @NotNull RenderType renderType() {
        return JUNGLE_SPIDER_EYES;
    }
}
