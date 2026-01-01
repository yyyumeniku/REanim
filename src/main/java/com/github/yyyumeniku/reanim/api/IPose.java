package com.github.yyyumeniku.reanim.api;

/**
 * Defines a static pose for a biped model.
 * All angles are in RADIANS.
 * 
 * Coordinate system:
 * - X = pitch (forward/backward tilt)
 * - Y = yaw (left/right rotation)
 * - Z = roll (sideways tilt)
 * 
 * Positive/negative values:
 * - Positive X = tilts forward
 * - Positive Y = rotates left
 * - Positive Z = rolls right
 */
public interface IPose {
    
    // Head rotation
    float getHeadRotateX();
    float getHeadRotateY();
    float getHeadRotateZ();
    
    // Right arm rotation
    float getRightArmRotateX();
    float getRightArmRotateY();
    float getRightArmRotateZ();
    
    // Left arm rotation
    float getLeftArmRotateX();
    float getLeftArmRotateY();
    float getLeftArmRotateZ();
    
    // Right leg rotation
    float getRightLegRotateX();
    float getRightLegRotateY();
    float getRightLegRotateZ();
    
    // Left leg rotation
    float getLeftLegRotateX();
    float getLeftLegRotateY();
    float getLeftLegRotateZ();
    
    // Body/torso rotation
    float getBodyRotateX();
    float getBodyRotateY();
    float getBodyRotateZ();
    
    // Which parts this pose overrides (for layering)
    default boolean overridesHead() { return true; }
    default boolean overridesRightArm() { return true; }
    default boolean overridesLeftArm() { return true; }
    default boolean overridesRightLeg() { return true; }
    default boolean overridesLeftLeg() { return true; }
    default boolean overridesBody() { return true; }
    
    // Optional: body part offsets (translation) - in model units
    default float getHeadOffsetX() { return 0; }
    default float getHeadOffsetY() { return 0; }
    default float getHeadOffsetZ() { return 0; }
    
    default float getRightArmOffsetX() { return 0; }
    default float getRightArmOffsetY() { return 0; }
    default float getRightArmOffsetZ() { return 0; }
    
    default float getLeftArmOffsetX() { return 0; }
    default float getLeftArmOffsetY() { return 0; }
    default float getLeftArmOffsetZ() { return 0; }
    
    default float getRightLegOffsetX() { return 0; }
    default float getRightLegOffsetY() { return 0; }
    default float getRightLegOffsetZ() { return 0; }
    
    default float getLeftLegOffsetX() { return 0; }
    default float getLeftLegOffsetY() { return 0; }
    default float getLeftLegOffsetZ() { return 0; }
    
    default float getBodyOffsetX() { return 0; }
    default float getBodyOffsetY() { return 0; }
    default float getBodyOffsetZ() { return 0; }
}
