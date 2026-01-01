package com.github.yyyumeniku.reanim.api;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import com.github.yyyumeniku.reanim.REanim;

import net.minecraft.util.ResourceLocation;

/**
 * Global registry for animations.
 * Register your animations here during mod initialization.
 * 
 * Example:
 * <pre>{@code
 * AnimationRegistry.register(new ProceduralAnimation(
 *     new ResourceLocation("mymod", "climb"),
 *     20, true,
 *     (progress, partial) -> { ... }
 * ));
 * }</pre>
 */
public final class AnimationRegistry {
    
    private static final ConcurrentHashMap<ResourceLocation, IAnimation> REGISTRY = new ConcurrentHashMap<>();
    
    private AnimationRegistry() {}
    
    /**
     * Register an animation.
     * @param animation The animation to register
     * @throws IllegalArgumentException if an animation with the same ID already exists
     */
    public static void register(IAnimation animation) {
        ResourceLocation id = animation.getId();
        if (REGISTRY.containsKey(id)) {
            throw new IllegalArgumentException("Animation already registered: " + id);
        }
        REGISTRY.put(id, animation);
        REanim.LOGGER.debug("Registered animation: {}", id);
    }
    
    /**
     * Register an animation, replacing any existing one with the same ID.
     * @param animation The animation to register
     */
    public static void registerOrReplace(IAnimation animation) {
        REGISTRY.put(animation.getId(), animation);
        REanim.LOGGER.debug("Registered/replaced animation: {}", animation.getId());
    }
    
    /**
     * Get an animation by ID.
     * @param id The animation ID
     * @return The animation, or null if not found
     */
    @Nullable
    public static IAnimation get(ResourceLocation id) {
        return REGISTRY.get(id);
    }
    
    /**
     * Check if an animation is registered.
     * @param id The animation ID
     * @return true if registered
     */
    public static boolean isRegistered(ResourceLocation id) {
        return REGISTRY.containsKey(id);
    }
    
    /**
     * Get all registered animations.
     * @return Collection of all animations (unmodifiable view)
     */
    public static Collection<IAnimation> getAll() {
        return REGISTRY.values();
    }
    
    /**
     * Remove an animation from the registry.
     * @param id The animation ID to remove
     * @return true if the animation was removed
     */
    public static boolean unregister(ResourceLocation id) {
        return REGISTRY.remove(id) != null;
    }
    
    /**
     * Clear all registered animations.
     * Use with caution - typically only for testing.
     */
    public static void clear() {
        REGISTRY.clear();
        REanim.LOGGER.warn("Animation registry cleared!");
    }
}
