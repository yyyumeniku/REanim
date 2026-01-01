package com.github.yyyumeniku.reanim.api;

import javax.annotation.Nullable;

import com.github.yyyumeniku.reanim.capability.AnimationProvider;
import com.github.yyyumeniku.reanim.capability.IAnimationCapability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * Main API entry point for REanim.
 * Use this class to interact with the animation system.
 * 
 * Example usage:
 * <pre>{@code
 * // Play an animation
 * AnimationAPI.play(player, new ResourceLocation("mymod", "climb"));
 * 
 * // Check if playing
 * if (AnimationAPI.isPlaying(player)) {
 *     // ...
 * }
 * 
 * // Stop animation
 * AnimationAPI.stop(player);
 * }</pre>
 */
public final class AnimationAPI {
    
    private AnimationAPI() {}
    
    /**
     * Get the animation controller for an entity.
     * @param entity The entity
     * @return The controller, or null if entity doesn't have animation capability
     */
    @Nullable
    public static IAnimationController getController(EntityLivingBase entity) {
        if (entity == null) return null;
        IAnimationCapability cap = entity.getCapability(AnimationProvider.ANIMATION_CAP, null);
        return cap != null ? cap.getController() : null;
    }
    
    /**
     * Play an animation on an entity.
     * @param entity The entity to animate
     * @param animationId The animation ID (must be registered)
     */
    public static void play(EntityLivingBase entity, ResourceLocation animationId) {
        IAnimationController controller = getController(entity);
        if (controller != null) {
            controller.play(animationId);
        }
    }
    
    /**
     * Play an animation with custom transition time.
     * @param entity The entity to animate
     * @param animationId The animation ID
     * @param transitionTicks Ticks to blend from current pose
     */
    public static void play(EntityLivingBase entity, ResourceLocation animationId, int transitionTicks) {
        IAnimationController controller = getController(entity);
        if (controller != null) {
            controller.play(animationId, transitionTicks);
        }
    }
    
    /**
     * Stop any animation on an entity.
     * @param entity The entity
     */
    public static void stop(EntityLivingBase entity) {
        IAnimationController controller = getController(entity);
        if (controller != null) {
            controller.stop();
        }
    }
    
    /**
     * Stop animation with a transition to idle.
     * @param entity The entity
     * @param transitionTicks Ticks to blend to idle pose
     */
    public static void stopWithTransition(EntityLivingBase entity, int transitionTicks) {
        IAnimationController controller = getController(entity);
        if (controller != null) {
            controller.stopWithTransition(transitionTicks);
        }
    }
    
    /**
     * Check if an entity is playing any animation.
     * @param entity The entity
     * @return true if an animation is playing
     */
    public static boolean isPlaying(EntityLivingBase entity) {
        IAnimationController controller = getController(entity);
        return controller != null && controller.isPlaying();
    }
    
    /**
     * Check if an entity is playing a specific animation.
     * @param entity The entity
     * @param animationId The animation ID
     * @return true if the specific animation is playing
     */
    public static boolean isPlaying(EntityLivingBase entity, ResourceLocation animationId) {
        IAnimationController controller = getController(entity);
        return controller != null && controller.isPlaying(animationId);
    }
    
    /**
     * Get the current pose for an entity.
     * @param entity The entity
     * @param partialTicks Render partial ticks
     * @return The current pose, or null if no animation is playing
     */
    @Nullable
    public static IPose getCurrentPose(EntityLivingBase entity, float partialTicks) {
        IAnimationController controller = getController(entity);
        return controller != null ? controller.getCurrentPose(partialTicks) : null;
    }
    
    /**
     * Set animation playback speed.
     * @param entity The entity
     * @param speed Speed multiplier (1.0 = normal)
     */
    public static void setSpeed(EntityLivingBase entity, float speed) {
        IAnimationController controller = getController(entity);
        if (controller != null) {
            controller.setSpeed(speed);
        }
    }
    
    /**
     * Pause/resume animation.
     * @param entity The entity
     * @param paused true to pause, false to resume
     */
    public static void setPaused(EntityLivingBase entity, boolean paused) {
        IAnimationController controller = getController(entity);
        if (controller != null) {
            controller.setPaused(paused);
        }
    }
}
