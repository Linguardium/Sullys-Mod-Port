package com.uraneptus.sullysmod.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.ThrownKnifeRenderState;
import com.uraneptus.sullysmod.common.entities.ThrownThrowingKnife;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.item.ItemDisplayContext;

public class ThrownThrowingKnifeRenderer extends EntityRenderer<ThrownThrowingKnife, ThrownKnifeRenderState> {
    private final ItemModelResolver itemModelResolver;
    private final ModelManager modelManager;
    public ThrownThrowingKnifeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemModelResolver = pContext.getItemModelResolver();
        modelManager = pContext.getModelManager();
    }

    @Override
    public ThrownKnifeRenderState createRenderState() {
        return new ThrownKnifeRenderState();
    }

    @Override
    public void extractRenderState(ThrownThrowingKnife knife, ThrownKnifeRenderState renderState, float tickDelta) {
        super.extractRenderState(knife, renderState, tickDelta);
        itemModelResolver.updateForNonLiving(renderState.item, knife.getPickupItem(),ItemDisplayContext.GROUND, knife);
        renderState.xRot= knife.getXRot(tickDelta);
        renderState.yRot = knife.getYRot(tickDelta);
        renderState.shake = knife.shakeTime - tickDelta;
    }

    @Override
    public void render(ThrownKnifeRenderState renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight) {
        poseStack.pushPose();
        ItemStackRenderState itemRenderState = renderState.item;
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot - 130.0F));
        itemRenderState.render(poseStack,multiBufferSource, pPackedLight, OverlayTexture.NO_OVERLAY);

//        if (!renderState.inGround || (renderState.lastState != null && renderState.lastState.is(SMBlockTags.PROJECTILES_BOUNCE_ON))) {
//            float time = renderState.tickCount + pPartialTicks;
//            pPoseStack.mulPose(Axis.ZP.rotationDegrees(time * 50F));
//        }

//        this.itemRenderer.render(itemstack, ItemDisplayContext.FIXED, false, poseStack, multiBufferSource, pPackedLight, OverlayTexture.NO_OVERLAY, bakedmodel);

        poseStack.popPose();
        super.render(renderState, poseStack, multiBufferSource, pPackedLight);
    }
}
