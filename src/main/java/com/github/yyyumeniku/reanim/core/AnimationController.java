package com.github.yyyumeniku.reanim.core;

import javax.annotation.Nullable;

import com.github.yyyumeniku.reanim.api.AnimationRegistry;
import com.github.yyyumeniku.reanim.api.IAnimation;
import com.github.yyyumeniku.reanim.api.IAnimationController;
import com.github.yyyumeniku.reanim.api.IPose;

import net.minecraft.util.ResourceLocation;

/**
 * Optimized animation controller for single entity.
 * Manages playback, transitions, and pose calculation.
 */
public final class AnimationController implements IAnimationController {
    
    @Nullable private IAnimation currentAnimation;
    @Nullable private ResourceLocation currentAnimationId;
    @Nullable private IPose previousPose;
    
    private int currentTick;
    private float speed = 1.0f;
    private boolean paused;
    private int transitionTicks;
    private int transitionTicksTotal;
    
    @Override
    public void play(ResourceLocation animationId) {
        IAnimation anim = AnimationRegistry.get(animationId);
        if (anim != null) {
            play(animationId, anim.getTransitionInTicks());
        }
    }
    
    @Override
    public void play(ResourceLocation animationId, int transitionTicks) {
        IAnimation newAnim = AnimationRegistry.get(animationId);
        if (newAnim == null || animationId.equals(currentAnimationId)) {
            return;
        }
        
        // Store previous pose for blending
        if (currentAnimation != null && transitionTicks > 0) {
            this.previousPose = currentAnimation.getPoseAtProgress(getProgress(), 0);
            this.transitionTicks = transitionTicks;
            this.transitionTicksTotal = transitionTicks;
        } else {
            this.previousPose = null;
            this.transitionTicks = 0;
            this.transitionTicksTotal = 0;
        }
        
        this.currentAnimation = newAnim;
        this.currentAnimationId = animationId;
        this.currentTick = 0;
        this.paused = false;
    }
    
    @Override
    public void stop() {
        currentAnimation = null;
        currentAnimationId = null;
        currentTick = 0;
        previousPose = null;
        transitionTicks = 0;
    }
    
    @Override
    public void stopWithTransition(int ticks) {
        if (currentAnimation != null && ticks > 0) {
            previousPose = currentAnimation.getPoseAtProgress(getProgress(), 0);
            transitionTicks = ticks;
            transitionTicksTotal = ticks;
        }
        currentAnimation = null;
        currentAnimationId = null;
        currentTick = 0;
    }
    
    @Override
    public boolean isPlaying() {
        return currentAnimation != null;
    }
    
    @Override
    public boolean isPlaying(ResourceLocation animationId) {
        return animationId != null && animationId.equals(currentAnimationId);
    }
    
    @Override
    @Nullable
    public ResourceLocation getCurrentAnimationId() {
        return currentAnimationId;
    }
    
    @Override
    public float getProgress() {
        if (currentAnimation == null) return 0;
        int duration = currentAnimation.getDurationTicks();
        return duration > 0 ? (float) currentTick / duration : 0;
    }
    
    @Override
    public int getCurrentTick() {
        return currentTick;
    }
    
    public void setCurrentTick(int tick) {
        this.currentTick = tick;
    }
    
    @Override
    public void setSpeed(float speed) {
        this.speed = Math.max(0, speed);
    }
    
    @Override
    public float getSpeed() {
        return speed;
    }
    
    @Override
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    @Override
    public boolean isPaused() {
        return paused;
    }
    
    @Override
    @Nullable
    public IPose getCurrentPose(float partialTicks) {
        // Handle transition blending
        if (transitionTicks > 0 && previousPose != null && transitionTicksTotal > 0) {
            float progress = 1.0f - (transitionTicks - partialTicks) / transitionTicksTotal;
            progress = Math.max(0, Math.min(1, progress));
            
            if (currentAnimation != null) {
                IPose target = currentAnimation.getPoseAtProgress(getProgress(), partialTicks);
                return PoseInterpolator.interpolate(previousPose, target, progress);
            }
            return previousPose;
        }
        
        if (currentAnimation == null) return null;
        
        // Calculate smooth progress with partial ticks
        int duration = currentAnimation.getDurationTicks();
        if (duration <= 0) {
            return currentAnimation.getPoseAtProgress(0, partialTicks);
        }
        
        float smoothProgress = paused ? getProgress() : (currentTick + partialTicks * speed) / duration;
        
        if (currentAnimation.isLooping()) {
            smoothProgress = smoothProgress % 1.0f;
            if (smoothProgress < 0) smoothProgress += 1.0f;
        } else {
            smoothProgress = Math.min(1.0f, smoothProgress);
        }
        
        return currentAnimation.getPoseAtProgress(smoothProgress, partialTicks);
    }
    
    @Override
    public void tick() {
        if (transitionTicks > 0) {
            transitionTicks--;
            if (transitionTicks <= 0) {
                previousPose = null;
            }
        }
        
        if (currentAnimation == null || paused) return;
        
        currentTick += (int) speed;
        if (speed != (int) speed) {
            // Handle fractional speeds with accumulation
            currentTick = (int) (currentTick + (speed - (int) speed));
        }
        
        int duration = currentAnimation.getDurationTicks();
        if (duration > 0) {
            if (currentAnimation.isLooping()) {
                currentTick = currentTick % duration;
            } else if (currentTick >= duration) {
                stop();
            }
        }
    }
}
