package com.uraneptus.sullysmod.client.renderer.entities.layer;

import com.uraneptus.sullysmod.client.model.JungleSpiderModel;
import com.uraneptus.sullysmod.client.renderer.entities.SMTextures;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.JungleSpiderRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.Nullable;

public class JungleSpiderBeneficialPatternLayer extends AbstractJungleSpiderPatternLayer {
    private static final RenderType BENEFICIAL_RENDER_TYPE = RenderType.entityTranslucent(SMTextures.JUNGLE_SPIDER_BENEFICIAL_EFFECT);

    public JungleSpiderBeneficialPatternLayer(RenderLayerParent<JungleSpiderRenderState, JungleSpiderModel> parent) {
        super(parent);
    }

    @Override
    protected @Nullable MobEffectInstance getMobEffectInstance(JungleSpiderRenderState renderState) {
        return renderState.venomData.beneficial();
    }

    @Override
    protected RenderType renderType() {
        return BENEFICIAL_RENDER_TYPE;
    }
}
