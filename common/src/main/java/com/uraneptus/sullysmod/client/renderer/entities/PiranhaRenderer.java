package com.uraneptus.sullysmod.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.uraneptus.sullysmod.client.model.PiranhaModel;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.PiranhaRenderState;
import com.uraneptus.sullysmod.common.entities.Piranha;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PiranhaRenderer extends MobRenderer<Piranha, PiranhaRenderState, PiranhaModel> {

    public PiranhaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PiranhaModel(pContext.bakeLayer(SMModelLayers.PIRANHA)), 0.3F);
    }

    @Override
    public PiranhaRenderState createRenderState() {
        return new PiranhaRenderState();
    }

    @Override
    public void extractRenderState(Piranha piranha, PiranhaRenderState renderState, float f) {
        super.extractRenderState(piranha, renderState, f);
        renderState.angrySwimState.copyFrom(piranha.angrySwimState);
        renderState.swimState.copyFrom(piranha.swimState);
        renderState.isLeaping = piranha.getLeaping();

    }

    @Override
    protected void setupRotations(PiranhaRenderState renderState, PoseStack poseStack, float bodyRotation, float scale) {
        super.setupRotations(renderState, poseStack, bodyRotation, scale);
        float f = 4.3F * Mth.sin(0.6F * renderState.ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!renderState.isInWater && !renderState.isLeaping) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }

    }

    @Override
    public ResourceLocation getTextureLocation(PiranhaRenderState renderState) {
        return SMTextures.PIRANHA;
    }
}
