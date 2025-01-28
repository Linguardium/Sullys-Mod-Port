package com.uraneptus.sullysmod.client.renderer.entities;

import com.uraneptus.sullysmod.client.model.BoulderingZombieModel;
import com.uraneptus.sullysmod.client.renderer.entities.renderstates.BoulderingZombieRenderState;
import com.uraneptus.sullysmod.common.entities.BoulderingZombie;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BoulderingZombieRenderer extends AbstractZombieRenderer<BoulderingZombie, BoulderingZombieRenderState, BoulderingZombieModel> {

    public BoulderingZombieRenderer(EntityRendererProvider.Context context) {
        // 			new DrownedModel(context.bakeLayer(ModelLayers.DROWNED)),
        //			new DrownedModel(context.bakeLayer(ModelLayers.DROWNED_BABY)),
        //			new DrownedModel(context.bakeLayer(ModelLayers.DROWNED_INNER_ARMOR)),
        //			new DrownedModel(context.bakeLayer(ModelLayers.DROWNED_OUTER_ARMOR)),
        //			new DrownedModel(context.bakeLayer(ModelLayers.DROWNED_BABY_INNER_ARMOR)),
        //			new DrownedModel(context.bakeLayer(ModelLayers.DROWNED_BABY_OUTER_ARMOR))
        super(context,
                new BoulderingZombieModel(context.bakeLayer(SMModelLayers.BOULDER_ZOMBIE)),
                new BoulderingZombieModel(context.bakeLayer(SMModelLayers.BOULDER_ZOMBIE_BABY)),
                new BoulderingZombieModel(context.bakeLayer(SMModelLayers.BOULDER_ZOMBIE_INNER_ARMOR)),
                new BoulderingZombieModel(context.bakeLayer(SMModelLayers.BOULDER_ZOMBIE_OUTER_ARMOR)),
                new BoulderingZombieModel(context.bakeLayer(SMModelLayers.BOULDER_ZOMBIE_INNER_ARMOR_BABY)),
                new BoulderingZombieModel(context.bakeLayer(SMModelLayers.BOULDER_ZOMBIE_OUTER_ARMOR_BABY))
        );
//        this.addLayer(new HumanoidArmorLayer<>(this, new BoulderingZombieModel<>(pContext.bakeLayer(BoulderingZombieModel.INNER_ARMOR)), new BoulderingZombieModel<>(pContext.bakeLayer(BoulderingZombieModel.OUTER_ARMOR)), pContext.getModelManager()));
    }

    @Override
    public BoulderingZombieRenderState createRenderState() {
        return new BoulderingZombieRenderState();
    }

    @Override
    public void extractRenderState(BoulderingZombie zombie, BoulderingZombieRenderState zombieRenderState, float f) {
        super.extractRenderState(zombie, zombieRenderState, f);
        zombieRenderState.climbAnimationState.copyFrom(zombie.climbAnimationState);
    }

    public ResourceLocation getTextureLocation(BoulderingZombieRenderState renderState) {
        return SMTextures.BOULDERING_ZOMBIE;
    }
}
