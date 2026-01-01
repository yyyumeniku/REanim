package com.github.yyyumeniku.reanim.capability;

import javax.annotation.Nullable;

import com.github.yyyumeniku.reanim.api.IAnimationController;
import com.github.yyyumeniku.reanim.core.AnimationController;

import net.minecraft.util.ResourceLocation;

/**
 * Default implementation of IAnimationCapability.
 */
public class AnimationCapability implements IAnimationCapability {
    
    private final AnimationController controller;
    
    public AnimationCapability() {
        this.controller = new AnimationController();
    }
    
    @Override
    public IAnimationController getController() {
        return controller;
    }
    
    @Override
    @Nullable
    public ResourceLocation getCurrentAnimationId() {
        return controller.getCurrentAnimationId();
    }
    
    @Override
    public void setCurrentAnimation(@Nullable ResourceLocation id) {
        if (id == null) {
            controller.stop();
        } else {
            controller.play(id);
        }
    }
    
    @Override
    public int getCurrentTick() {
        return controller.getCurrentTick();
    }
    
    @Override
    public void setCurrentTick(int tick) {
        controller.setCurrentTick(tick);
    }
    
    @Override
    public void tick() {
        controller.tick();
    }
}
