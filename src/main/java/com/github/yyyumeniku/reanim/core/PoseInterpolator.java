package com.github.yyyumeniku.reanim.core;

import com.github.yyyumeniku.reanim.api.IPose;
import com.github.yyyumeniku.reanim.api.PoseBuilder;

/**
 * Utility class for interpolating between poses.
 * Used for smooth transitions between animations.
 */
public final class PoseInterpolator {
    
    private PoseInterpolator() {}
    
    /**
     * Linearly interpolate between two poses.
     * @param from Starting pose
     * @param to Target pose
     * @param progress 0.0 = from, 1.0 = to
     * @return Interpolated pose
     */
    public static IPose interpolate(IPose from, IPose to, float progress) {
        if (from == null) return to;
        if (to == null) return from;
        if (progress <= 0) return from;
        if (progress >= 1) return to;
        
        float inv = 1.0f - progress;
        
        return PoseBuilder.create()
            .head(
                from.getHeadRotateX() * inv + to.getHeadRotateX() * progress,
                from.getHeadRotateY() * inv + to.getHeadRotateY() * progress,
                from.getHeadRotateZ() * inv + to.getHeadRotateZ() * progress
            )
            .rightArm(
                from.getRightArmRotateX() * inv + to.getRightArmRotateX() * progress,
                from.getRightArmRotateY() * inv + to.getRightArmRotateY() * progress,
                from.getRightArmRotateZ() * inv + to.getRightArmRotateZ() * progress
            )
            .leftArm(
                from.getLeftArmRotateX() * inv + to.getLeftArmRotateX() * progress,
                from.getLeftArmRotateY() * inv + to.getLeftArmRotateY() * progress,
                from.getLeftArmRotateZ() * inv + to.getLeftArmRotateZ() * progress
            )
            .rightLeg(
                from.getRightLegRotateX() * inv + to.getRightLegRotateX() * progress,
                from.getRightLegRotateY() * inv + to.getRightLegRotateY() * progress,
                from.getRightLegRotateZ() * inv + to.getRightLegRotateZ() * progress
            )
            .leftLeg(
                from.getLeftLegRotateX() * inv + to.getLeftLegRotateX() * progress,
                from.getLeftLegRotateY() * inv + to.getLeftLegRotateY() * progress,
                from.getLeftLegRotateZ() * inv + to.getLeftLegRotateZ() * progress
            )
            .body(
                from.getBodyRotateX() * inv + to.getBodyRotateX() * progress,
                from.getBodyRotateY() * inv + to.getBodyRotateY() * progress,
                from.getBodyRotateZ() * inv + to.getBodyRotateZ() * progress
            )
            .overrideHead(to.overridesHead())
            .overrideRightArm(to.overridesRightArm())
            .overrideLeftArm(to.overridesLeftArm())
            .overrideRightLeg(to.overridesRightLeg())
            .overrideLeftLeg(to.overridesLeftLeg())
            .overrideBody(to.overridesBody())
            .build();
    }
    
    /**
     * Ease-in interpolation (starts slow, ends fast).
     */
    public static IPose easeIn(IPose from, IPose to, float progress) {
        float easedProgress = progress * progress;
        return interpolate(from, to, easedProgress);
    }
    
    /**
     * Ease-out interpolation (starts fast, ends slow).
     */
    public static IPose easeOut(IPose from, IPose to, float progress) {
        float easedProgress = 1.0f - (1.0f - progress) * (1.0f - progress);
        return interpolate(from, to, easedProgress);
    }
    
    /**
     * Ease-in-out interpolation (smooth start and end).
     */
    public static IPose easeInOut(IPose from, IPose to, float progress) {
        float easedProgress;
        if (progress < 0.5f) {
            easedProgress = 2.0f * progress * progress;
        } else {
            easedProgress = 1.0f - (float) Math.pow(-2.0f * progress + 2.0f, 2) / 2.0f;
        }
        return interpolate(from, to, easedProgress);
    }
}
