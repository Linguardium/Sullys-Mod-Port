package com.uraneptus.sullysmod.client.model;

import com.uraneptus.sullysmod.client.renderer.entities.renderstates.TortoiseShellRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class TortoiseShellModel extends EntityModel<TortoiseShellRenderState> {

    private final ModelPart shell;
    public final ModelPart saddle;

    public TortoiseShellModel(ModelPart root) {
        super(root);
        this.shell = root.getChild("shell");
        this.saddle = root.getChild("workstationSaddle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition shell = partdefinition.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -17.0F, -8.0F, 16.0F, 14.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-8.0F, -17.0F, -8.0F, 16.0F, 14.0F, 18.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 13.0F, -0.5F, 0.0F, 0.0F, -3.1416F));
        shell.addOrReplaceChild("workstationSaddle", CubeListBuilder.create().texOffs(0, 64).addBox(-8.0F, -18.0F, -7.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(TortoiseShellRenderState renderState) {
        this.shell.getChild("workstationSaddle").visible = false;
    }
}