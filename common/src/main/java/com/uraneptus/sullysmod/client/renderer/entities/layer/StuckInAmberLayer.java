package com.uraneptus.sullysmod.client.renderer.entities.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class StuckInAmberLayer extends RenderLayer<LivingEntityRenderState, EntityModel<LivingEntityRenderState>> {

    public StuckInAmberLayer(RenderLayerParent<LivingEntityRenderState, EntityModel<LivingEntityRenderState>> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, LivingEntityRenderState entityRenderState, float f, float g) {
//        SMEntityCap.getCapOptional(pLivingEntity).ifPresent(cap -> {
//            pPoseStack.pushPose();
//            if (cap.stuckInAmber) {
//                VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityTranslucent(location("textures/misc/amber_layer.png")));
//                this.getParentModel().renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.65F);
//            }
//            pPoseStack.popPose();
//        });
    }
}
