package com.uraneptus.sullysmod.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.uraneptus.sullysmod.client.model.LanternfishModel;
import com.uraneptus.sullysmod.client.renderer.entities.layer.LanternfishGlowLayer;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.LanternFishRenderState;
import com.uraneptus.sullysmod.common.entities.Lanternfish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LanternfishRenderer extends MobRenderer<Lanternfish, LanternFishRenderState, LanternfishModel> {

    public LanternfishRenderer(EntityRendererProvider.Context context) {
        super(context, new LanternfishModel(context.bakeLayer(SMModelLayers.LANTERN_FISH)), 0.3F);
        this.addLayer(new LanternfishGlowLayer(this));
    }

    @Override
    public LanternFishRenderState createRenderState() {
        return new LanternFishRenderState();
    }

    @Override
    public void extractRenderState(Lanternfish fish, LanternFishRenderState renderState, float f) {
        super.extractRenderState(fish, renderState, f);
        renderState.lanternIsGlowing = fish.renderGlow();

    }

    @Override
    protected void setupRotations(LanternFishRenderState renderState, PoseStack poseStack, float bodyRotation, float scale) {
        super.setupRotations(renderState, poseStack, bodyRotation, scale);
        float f = 4.3F * Mth.sin(0.6F * renderState.ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!renderState.isInWater) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(LanternFishRenderState livingEntityRenderState) {
        return SMTextures.LANTERN_FISH;
    }
}
