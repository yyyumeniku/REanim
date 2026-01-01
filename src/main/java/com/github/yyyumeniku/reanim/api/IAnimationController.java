package com.github.yyyumeniku.reanim.api;

import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;

/**
 * Controls animation playback for a single entity.
 * Each animatable entity has its own controller instance.
 */
public interface IAnimationController {
    
    /** Play an animation by ID (uses default transition time) */
    void play(ResourceLocation animationId);
    
    /** Play an animation with custom transition time */
    void play(ResourceLocation animationId, int transitionTicks);
    
    /** Stop the current animation immediately */
    void stop();
    
    /** Stop the current animation with a transition to idle */
    void stopWithTransition(int transitionTicks);
    
    /** Is any animation currently playing? */
    boolean isPlaying();
    
    /** Is a specific animation currently playing? */
    boolean isPlaying(ResourceLocation animationId);
    
    /** Get the current animation ID (null if none) */
    @Nullable
    ResourceLocation getCurrentAnimationId();
    
    /** Get playback progress (0.0 to 1.0) */
    float getProgress();
    
    /** Get current tick in the animation */
    int getCurrentTick();
    
    /** Set playback speed multiplier (1.0 = normal, 2.0 = double speed) */
    void setSpeed(float speed);
    
    /** Get current playback speed */
    float getSpeed();
    
    /** Pause/resume animation */
    void setPaused(boolean paused);
    
    /** Is the animation paused? */
    boolean isPaused();
    
    /** 
     * Get the current pose (with interpolation).
     * Returns null if no animation is playing.
     * @param partialTicks Render partial ticks for smooth interpolation
     */
    @Nullable
    IPose getCurrentPose(float partialTicks);
    
    /** Called every game tick to update the animation */
    void tick();
}
