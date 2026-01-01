package com.github.yyyumeniku.reanim.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * Represents an animation that can be played on an entity.
 * 
 * Animations can be:
 * - Static poses (StaticPoseAnimation)
 * - Keyframe sequences (KeyframeAnimation)
 * - Procedural/math-based (ProceduralAnimation)
 */
public interface IAnimation {
    
    /** Unique identifier for this animation */
    ResourceLocation getId();
    
    /** 
     * Get the pose at a specific progress.
     * @param progress 0.0 to 1.0 for one cycle (loops wrap around)
     * @param partialTicks Render partial ticks for smooth interpolation
     * @return The pose to apply at this point in the animation
     */
    IPose getPoseAtProgress(float progress, float partialTicks);
    
    /** Duration in game ticks (20 = 1 second) */
    int getDurationTicks();
    
    /** Whether this animation loops */
    boolean isLooping();
    
    /** Playback speed multiplier (1.0 = normal) */
    default float getSpeed() { return 1.0f; }
    
    /** Priority for animation layering (higher = takes precedence) */
    default int getPriority() { return 0; }
    
    /** Transition time when blending FROM another animation to this one (in ticks) */
    default int getTransitionInTicks() { return 5; }
    
    /** Transition time when blending FROM this animation to another (in ticks) */
    default int getTransitionOutTicks() { return 5; }
    
    /** 
     * Whether this animation should play (condition check).
     * Called every tick to determine if animation should continue.
     */
    default boolean shouldPlay(EntityLivingBase entity) { return true; }
    
    /** Called when animation starts playing */
    default void onStart(EntityLivingBase entity) {}
    
    /** Called when animation ends or is stopped */
    default void onEnd(EntityLivingBase entity) {}
    
    /** Called every tick while this animation is playing */
    default void onTick(EntityLivingBase entity, int currentTick) {}
}
