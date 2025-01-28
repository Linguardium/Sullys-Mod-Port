package com.uraneptus.sullysmod.client.renderer.entities.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.uraneptus.sullysmod.client.model.JungleSpiderModel;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.JungleSpiderRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalInt;

public abstract class AbstractJungleSpiderPatternLayer extends RenderLayer<JungleSpiderRenderState, JungleSpiderModel> {
    public AbstractJungleSpiderPatternLayer(RenderLayerParent<JungleSpiderRenderState, JungleSpiderModel> parent) {
        super(parent);
    }

    @Nullable
    protected abstract MobEffectInstance getMobEffectInstance(JungleSpiderRenderState renderState);

    protected abstract RenderType renderType();

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, JungleSpiderRenderState renderState, float yRot, float xRot) {
        MobEffectInstance mobEffect = this.getMobEffectInstance(renderState);
        if (mobEffect == null) return;
        renderEffectLayer(mobEffect, poseStack, multiBufferSource, packedLight, renderState, yRot, xRot);
    }

    public void renderEffectLayer(MobEffectInstance instance, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, JungleSpiderRenderState renderState, float yRot, float xRot) {
        OptionalInt oColor = PotionContents.getColorOptional(List.of(instance));
        if (oColor.isEmpty()) return;
        int color = ARGB.scaleRGB(oColor.getAsInt(), 2);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.renderType());
        this.getParentModel().renderToBuffer(poseStack,vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), color);
    }
}
