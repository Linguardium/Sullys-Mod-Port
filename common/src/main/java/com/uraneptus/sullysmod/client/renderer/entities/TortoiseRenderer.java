package com.uraneptus.sullysmod.client.renderer.entities;

import com.uraneptus.sullysmod.client.model.TortoiseModel;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.TortoiseRenderState;
import com.uraneptus.sullysmod.common.entities.Tortoise;
import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;

import static com.uraneptus.sullysmod.core.registry.SMWorkstationTypes.CRAFTING_TABLE_WORKSTATION_TYPE;
import static com.uraneptus.sullysmod.core.registry.SMWorkstationTypes.JUKEBOX_WORKSTATION_TYPE;

public class TortoiseRenderer extends MobRenderer<Tortoise, TortoiseRenderState, TortoiseModel> {

    public TortoiseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TortoiseModel(pContext.bakeLayer(SMModelLayers.TORTOISE)), 0.75F);
    }

    @Override
    public TortoiseRenderState createRenderState() {
        return new TortoiseRenderState();
    }

    @Override
    public void extractRenderState(Tortoise livingEntity, TortoiseRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        ArmedEntityRenderState.extractArmedEntityRenderState(livingEntity, livingEntityRenderState, itemModelResolver);
        livingEntityRenderState.workstationRenderState.workstationType = livingEntity.workstation().getWorkstationType();
        livingEntityRenderState.hideTimer = livingEntity.getHideTimerDuration();
        livingEntityRenderState.hideState.copyFrom(livingEntity.hideState);
        livingEntityRenderState.hiddenState.copyFrom(livingEntity.hiddenState);
        livingEntityRenderState.reveal_state.copyFrom(livingEntity.reveal_state);

    }

    @Override
    public ResourceLocation getTextureLocation(TortoiseRenderState renderState) {
        SMWorkstationTypes.WorkstationType<?> workstationType = renderState.workstationRenderState.workstationType;
        if (workstationType == CRAFTING_TABLE_WORKSTATION_TYPE.get()) return SMTextures.TORTOISE_CRAFTING;
        if (workstationType == JUKEBOX_WORKSTATION_TYPE.get()) return SMTextures.TORTOISE_JUKEBOX;
        //TODO: default tortoise texture

        return SMTextures.TORTOISE_JUKEBOX;

    }

//    @Override
//    public void render(E pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
//        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

//        if (pEntity.isBaby()) {
//            pPoseStack.scale(0.15F, 0.15F, 0.15F);
//            this.shadowRadius *= 0.15F;
//        }
//    }

//    @Override
//    public ResourceLocation getTextureLocation(E pEntity) {
//        return pEntity.isCraftingTable() ? TEXTURE_CRAFTING : TEXTURE_JUKEBOX;
//    }
}
