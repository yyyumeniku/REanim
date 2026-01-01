package com.github.yyyumeniku.reanim.animation;

import com.github.yyyumeniku.reanim.api.IAnimation;
import com.github.yyyumeniku.reanim.api.IPose;
import com.github.yyyumeniku.reanim.api.PoseBuilder;

import net.minecraft.util.ResourceLocation;

/**
 * Animation that holds a single static pose.
 * Optionally adds trembling effect for stamina exhaustion.
 */
public final class StaticPoseAnimation implements IAnimation {
    
    private final ResourceLocation id;
    private final IPose pose;
    private final float trembleIntensity;
    private final int transitionTicks;
    
    // Pre-calculated trembling offsets for performance
    private static final float[] TREMBLE_OFFSETS = new float[64];
    static {
        for (int i = 0; i < TREMBLE_OFFSETS.length; i++) {
            TREMBLE_OFFSETS[i] = (float) Math.sin(i * 0.7) * 0.5f;
        }
    }
    
    public StaticPoseAnimation(ResourceLocation id, IPose pose) {
        this(id, pose, 0, 5);
    }
    
    public StaticPoseAnimation(ResourceLocation id, IPose pose, float trembleIntensity) {
        this(id, pose, trembleIntensity, 5);
    }
    
    public StaticPoseAnimation(ResourceLocation id, IPose pose, float trembleIntensity, int transitionTicks) {
        this.id = id;
        this.pose = pose;
        this.trembleIntensity = trembleIntensity;
        this.transitionTicks = transitionTicks;
    }
    
    @Override
    public ResourceLocation getId() {
        return id;
    }
    
    @Override
    public IPose getPoseAtProgress(float progress, float partialTicks) {
        if (trembleIntensity <= 0) {
            return pose;
        }
        
        // Use pre-calculated offsets with time-based index
        long time = System.currentTimeMillis();
        int index = (int) ((time / 20) % TREMBLE_OFFSETS.length);
        float offset = TREMBLE_OFFSETS[index] * trembleIntensity;
        
        return PoseBuilder.create()
            .head(pose.getHeadRotateX() + offset, pose.getHeadRotateY(), pose.getHeadRotateZ())
            .body(pose.getBodyRotateX() + offset * 0.3f, pose.getBodyRotateY(), pose.getBodyRotateZ())
            .leftArm(pose.getLeftArmRotateX() + offset, pose.getLeftArmRotateY(), pose.getLeftArmRotateZ())
            .rightArm(pose.getRightArmRotateX() - offset, pose.getRightArmRotateY(), pose.getRightArmRotateZ())
            .leftLeg(pose.getLeftLegRotateX(), pose.getLeftLegRotateY(), pose.getLeftLegRotateZ())
            .rightLeg(pose.getRightLegRotateX(), pose.getRightLegRotateY(), pose.getRightLegRotateZ())
            .build();
    }
    
    @Override
    public int getDurationTicks() {
        return 1;
    }
    
    @Override
    public boolean isLooping() {
        return true;
    }
    
    @Override
    public int getTransitionInTicks() {
        return transitionTicks;
    }
    
    public IPose getBasePose() {
        return pose;
    }
    
    public float getTrembleIntensity() {
        return trembleIntensity;
    }
}
