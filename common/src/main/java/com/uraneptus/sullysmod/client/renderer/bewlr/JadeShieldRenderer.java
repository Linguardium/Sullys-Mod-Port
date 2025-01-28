package com.uraneptus.sullysmod.client.renderer.bewlr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.uraneptus.sullysmod.client.model.JadeShieldModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static com.uraneptus.sullysmod.core.other.SMLocationUtil.location;

public class JadeShieldRenderer implements SpecialModelRenderer<Boolean> {
    public static final Material JADE_SHIELD_TEXTURE = new Material(Sheets.SHIELD_SHEET, location("entity/shield/jade_shield"));
    public final JadeShieldModel model;

    public JadeShieldRenderer(JadeShieldModel model) {
        this.model = model;
    }

    @Override
    public void render(@Nullable Boolean isGlowingArgument, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, boolean canGlow) {
        boolean isGlowing = isGlowingArgument != null && isGlowingArgument;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexconsumer = JADE_SHIELD_TEXTURE.buffer(multiBufferSource, this.model::renderType,true, isGlowing);
        //VertexConsumer vertexconsumer = material.sprite().wrap(ItemRenderer.getFoilBuffer(multiBufferSource, this.model.renderType(material.atlasLocation()), true, isGlowing));

        this.model.handle().render(poseStack, vertexconsumer, packedLight, packedOverlay);
        this.model.plate().render(poseStack, vertexconsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }

    @Override
    public @Nullable Boolean extractArgument(ItemStack itemStack) {
        return itemStack.hasFoil();
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final JadeShieldRenderer.Unbaked INSTANCE = new JadeShieldRenderer.Unbaked();
        public static final MapCodec<JadeShieldRenderer.Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public MapCodec<JadeShieldRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            return new JadeShieldRenderer(new JadeShieldModel(entityModelSet.bakeLayer(JadeShieldModel.LAYER_LOCATION)));
        }
    }

}
