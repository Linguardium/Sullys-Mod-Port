package com.uraneptus.sullysmod.client.renderer.entities.renderstates;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class TortoiseShellRenderState extends EntityRenderState {
    public final WorkstationRenderState workstationRenderState = new WorkstationRenderState();
    public float spinTime = 0;
    public float hurtTime = 0;
    public float damage = 0;
    public float hurtDir = 0;
}
