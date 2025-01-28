package com.uraneptus.sullysmod.client.renderer.entities.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.uraneptus.sullysmod.client.model.LanternfishModel;
import com.uraneptus.sullysmod.client.renderer.entities.SMTextures;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.LanternFishRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;


public class LanternfishGlowLayer extends RenderLayer<LanternFishRenderState, LanternfishModel> {
    private static final RenderType EYES_RENDER_TYPE = RenderType.eyes(SMTextures.LANTERN_FISH_GLOW);
    public LanternfishGlowLayer(RenderLayerParent<LanternFishRenderState, LanternfishModel> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, LanternFishRenderState renderState, float yRot, float xRot) {
        if (renderState.lanternIsGlowing) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(EYES_RENDER_TYPE);
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        }
    }
}
