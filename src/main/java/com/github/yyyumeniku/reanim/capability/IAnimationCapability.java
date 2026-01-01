package com.github.yyyumeniku.reanim.capability;

import javax.annotation.Nullable;

import com.github.yyyumeniku.reanim.api.IAnimationController;

import net.minecraft.util.ResourceLocation;

/**
 * Capability interface for entities that can be animated.
 */
public interface IAnimationCapability {
    
    /** Get the animation controller for this entity */
    IAnimationController getController();
    
    /** Get current animation ID (for syncing) */
    @Nullable
    ResourceLocation getCurrentAnimationId();
    
    /** Set current animation ID (used for syncing from server) */
    void setCurrentAnimation(@Nullable ResourceLocation id);
    
    /** Get current tick in the animation (for syncing) */
    int getCurrentTick();
    
    /** Set current tick (used for syncing from server) */
    void setCurrentTick(int tick);
    
    /** Called every game tick to update the animation */
    void tick();
}
