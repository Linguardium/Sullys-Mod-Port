package com.uraneptus.sullysmod.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.uraneptus.sullysmod.client.model.TortoiseShellModel;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.TortoiseShellRenderState;
import com.uraneptus.sullysmod.common.entities.TortoiseShell;
import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

import static com.uraneptus.sullysmod.core.registry.SMWorkstationTypes.CRAFTING_TABLE_WORKSTATION_TYPE;
import static com.uraneptus.sullysmod.core.registry.SMWorkstationTypes.JUKEBOX_WORKSTATION_TYPE;

public class TortoiseShellRenderer extends EntityRenderer<TortoiseShell, TortoiseShellRenderState> {
    protected final TortoiseShellModel model;

    public TortoiseShellRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.75F;
        this.model = new TortoiseShellModel(context.bakeLayer(SMModelLayers.TORTOISE_SHELL));
    }

    @Override
    public TortoiseShellRenderState createRenderState() {
        return new TortoiseShellRenderState();
    }

    @Override
    public void extractRenderState(TortoiseShell entity, TortoiseShellRenderState entityRenderState, float tickDelta) {
        super.extractRenderState(entity, entityRenderState, tickDelta);
        entityRenderState.workstationRenderState.workstationType = entity.workstation().getWorkstationType();
        entityRenderState.spinTime = entity.getSpinTicksEntityData();
        if (entityRenderState.spinTime > 0 ) entityRenderState.spinTime += tickDelta;
        entityRenderState.hurtTime = entity.getHurtTime() - tickDelta;
        entityRenderState.damage = Math.max(entity.getDamage() - tickDelta, 0);
        entityRenderState.hurtDir = entity.getHurtDir();
    }

    @Override
    public void render(TortoiseShellRenderState renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight) {
        super.render(renderState, poseStack, multiBufferSource, pPackedLight);
        poseStack.pushPose();
        poseStack.translate(0.0D, -1.0F, 0.0D);

        if (renderState.spinTime > 0) {
            poseStack.mulPose(new Quaternionf().rotateY(renderState.spinTime * 0.56F));
        }

        if (renderState.damage > 0.0F) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(renderState.hurtTime) * renderState.hurtTime * renderState.damage / 96.0F * renderState.hurtDir));
        }

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(this.model.renderType(this.getTextureLocation(renderState)));
        this.model.saddle.visible = renderState.workstationRenderState.workstationType != SMWorkstationTypes.EMPTY_WORKSTATION_TYPE.get();
        this.model.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        poseStack.popPose();
    }

    public ResourceLocation getTextureLocation(TortoiseShellRenderState renderState) {
        SMWorkstationTypes.WorkstationType<?> workstationType = renderState.workstationRenderState.workstationType;
        if (workstationType == CRAFTING_TABLE_WORKSTATION_TYPE.get()) return SMTextures.TORTOISE_CRAFTING;
        if (workstationType == JUKEBOX_WORKSTATION_TYPE.get()) return SMTextures.TORTOISE_JUKEBOX;
        //TODO: default tortoise shell textures

        return SMTextures.TORTOISE_JUKEBOX;

    }
 //    @Override
//    public ResourceLocation getTextureLocation(E pEntity) {
//        return pEntity.isCraftingTable() ? TEXTURE_CRAFTING : TEXTURE_JUKEBOX;
//    }
}
