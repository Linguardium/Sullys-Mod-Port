package com.uraneptus.sullysmod.client.renderer.entities;

import com.uraneptus.sullysmod.client.model.JungleSpiderModel;
import com.uraneptus.sullysmod.client.renderer.entities.layer.JungleSpiderBeneficialPatternLayer;
import com.uraneptus.sullysmod.client.renderer.entities.layer.JungleSpiderEyesLayer;
import com.uraneptus.sullysmod.client.renderer.entities.layer.JungleSpiderHarmfulPatternLayer;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.JungleSpiderRenderState;
import com.uraneptus.sullysmod.common.entities.JungleSpider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JungleSpiderRenderer extends MobRenderer<JungleSpider, JungleSpiderRenderState, JungleSpiderModel> {

    public JungleSpiderRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new JungleSpiderModel(pContext.bakeLayer(SMModelLayers.JUNGLE_SPIDER)), 0.65F);
        this.addLayer(new JungleSpiderEyesLayer(this));
        this.addLayer(new JungleSpiderBeneficialPatternLayer(this));
        this.addLayer(new JungleSpiderHarmfulPatternLayer(this));
    }

    @Override
    public JungleSpiderRenderState createRenderState() {
        return new JungleSpiderRenderState();
    }

    @Override
    public void extractRenderState(JungleSpider livingEntity, JungleSpiderRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.venomData = livingEntity.venomData();
    }

    @Override
    public ResourceLocation getTextureLocation(JungleSpiderRenderState renderState) {
        return SMTextures.JUNGLE_SPIDER;
    }

//    @Override
//    protected void scale(@NotNull JungleSpider pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
//        pPoseStack.scale(0.85F, 0.85F, 0.85F);
//    }


//    @Override
//    protected float getFlipDegrees(@NotNull T pLivingEntity) {
//        return 180.0F;
//    }

//    @Override
//    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
//        return SPIDER_LOCATION;
//    }
}
