package com.uraneptus.sullysmod.client.renderer.entities.renderstates;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.WalkAnimationState;

public class TortoiseRenderState extends ArmedEntityRenderState {
    public final AnimationState hideState = new AnimationState();
    public final AnimationState hiddenState = new AnimationState();
    public final AnimationState reveal_state = new AnimationState();
    public final WalkAnimationState walkAnimation = new WalkAnimationState();

    public final WorkstationRenderState workstationRenderState = new WorkstationRenderState();
    public float hideTimer;
}
