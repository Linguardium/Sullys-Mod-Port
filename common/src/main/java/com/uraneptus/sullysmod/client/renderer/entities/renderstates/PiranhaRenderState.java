package com.uraneptus.sullysmod.client.renderer.entities.renderstates;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PiranhaRenderState extends LivingEntityRenderState {
    public final AnimationState swimState = new AnimationState();
    public final AnimationState angrySwimState = new AnimationState();
    public boolean isLeaping = false;
}
