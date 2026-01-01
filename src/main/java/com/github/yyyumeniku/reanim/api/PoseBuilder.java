package com.github.yyyumeniku.reanim.api;

/**
 * Fluent builder for creating poses.
 * 
 * Usage:
 * <pre>{@code
 *   IPose pose = PoseBuilder.create()
 *       .rightArm(-2.2f, -0.2f, 0.35f)  // X, Y, Z rotation in radians
 *       .leftArm(-2.2f, 0.2f, -0.35f)
 *       .rightLeg(0.2f, 0, 0)
 *       .leftLeg(0.2f, 0, 0)
 *       .body(0.25f, 0, 0)
 *       .head(-0.15f, 0, 0)
 *       .build();
 * }</pre>
 */
public class PoseBuilder {

    private float headX, headY, headZ;
    private float rightArmX, rightArmY, rightArmZ;
    private float leftArmX, leftArmY, leftArmZ;
    private float rightLegX, rightLegY, rightLegZ;
    private float leftLegX, leftLegY, leftLegZ;
    private float bodyX, bodyY, bodyZ;
    
    private boolean overrideHead = true;
    private boolean overrideRightArm = true;
    private boolean overrideLeftArm = true;
    private boolean overrideRightLeg = true;
    private boolean overrideLeftLeg = true;
    private boolean overrideBody = true;

    private PoseBuilder() {}

    public static PoseBuilder create() {
        return new PoseBuilder();
    }

    public PoseBuilder head(float x, float y, float z) {
        this.headX = x;
        this.headY = y;
        this.headZ = z;
        return this;
    }

    public PoseBuilder rightArm(float x, float y, float z) {
        this.rightArmX = x;
        this.rightArmY = y;
        this.rightArmZ = z;
        return this;
    }

    public PoseBuilder leftArm(float x, float y, float z) {
        this.leftArmX = x;
        this.leftArmY = y;
        this.leftArmZ = z;
        return this;
    }

    public PoseBuilder rightLeg(float x, float y, float z) {
        this.rightLegX = x;
        this.rightLegY = y;
        this.rightLegZ = z;
        return this;
    }

    public PoseBuilder leftLeg(float x, float y, float z) {
        this.leftLegX = x;
        this.leftLegY = y;
        this.leftLegZ = z;
        return this;
    }

    public PoseBuilder body(float x, float y, float z) {
        this.bodyX = x;
        this.bodyY = y;
        this.bodyZ = z;
        return this;
    }

    public PoseBuilder overrideHead(boolean override) {
        this.overrideHead = override;
        return this;
    }

    public PoseBuilder overrideRightArm(boolean override) {
        this.overrideRightArm = override;
        return this;
    }

    public PoseBuilder overrideLeftArm(boolean override) {
        this.overrideLeftArm = override;
        return this;
    }

    public PoseBuilder overrideArms(boolean override) {
        this.overrideRightArm = override;
        this.overrideLeftArm = override;
        return this;
    }

    public PoseBuilder overrideRightLeg(boolean override) {
        this.overrideRightLeg = override;
        return this;
    }

    public PoseBuilder overrideLeftLeg(boolean override) {
        this.overrideLeftLeg = override;
        return this;
    }

    public PoseBuilder overrideLegs(boolean override) {
        this.overrideRightLeg = override;
        this.overrideLeftLeg = override;
        return this;
    }

    public PoseBuilder overrideBody(boolean override) {
        this.overrideBody = override;
        return this;
    }

    public IPose build() {
        return new BuiltPose(this);
    }

    /**
     * Internal implementation of IPose created by the builder.
     */
    private static class BuiltPose implements IPose {
        private final float headX, headY, headZ;
        private final float rightArmX, rightArmY, rightArmZ;
        private final float leftArmX, leftArmY, leftArmZ;
        private final float rightLegX, rightLegY, rightLegZ;
        private final float leftLegX, leftLegY, leftLegZ;
        private final float bodyX, bodyY, bodyZ;
        private final boolean overrideHead, overrideRightArm, overrideLeftArm;
        private final boolean overrideRightLeg, overrideLeftLeg, overrideBody;

        private BuiltPose(PoseBuilder builder) {
            this.headX = builder.headX;
            this.headY = builder.headY;
            this.headZ = builder.headZ;
            this.rightArmX = builder.rightArmX;
            this.rightArmY = builder.rightArmY;
            this.rightArmZ = builder.rightArmZ;
            this.leftArmX = builder.leftArmX;
            this.leftArmY = builder.leftArmY;
            this.leftArmZ = builder.leftArmZ;
            this.rightLegX = builder.rightLegX;
            this.rightLegY = builder.rightLegY;
            this.rightLegZ = builder.rightLegZ;
            this.leftLegX = builder.leftLegX;
            this.leftLegY = builder.leftLegY;
            this.leftLegZ = builder.leftLegZ;
            this.bodyX = builder.bodyX;
            this.bodyY = builder.bodyY;
            this.bodyZ = builder.bodyZ;
            this.overrideHead = builder.overrideHead;
            this.overrideRightArm = builder.overrideRightArm;
            this.overrideLeftArm = builder.overrideLeftArm;
            this.overrideRightLeg = builder.overrideRightLeg;
            this.overrideLeftLeg = builder.overrideLeftLeg;
            this.overrideBody = builder.overrideBody;
        }

        @Override public float getHeadRotateX() { return headX; }
        @Override public float getHeadRotateY() { return headY; }
        @Override public float getHeadRotateZ() { return headZ; }
        @Override public float getRightArmRotateX() { return rightArmX; }
        @Override public float getRightArmRotateY() { return rightArmY; }
        @Override public float getRightArmRotateZ() { return rightArmZ; }
        @Override public float getLeftArmRotateX() { return leftArmX; }
        @Override public float getLeftArmRotateY() { return leftArmY; }
        @Override public float getLeftArmRotateZ() { return leftArmZ; }
        @Override public float getRightLegRotateX() { return rightLegX; }
        @Override public float getRightLegRotateY() { return rightLegY; }
        @Override public float getRightLegRotateZ() { return rightLegZ; }
        @Override public float getLeftLegRotateX() { return leftLegX; }
        @Override public float getLeftLegRotateY() { return leftLegY; }
        @Override public float getLeftLegRotateZ() { return leftLegZ; }
        @Override public float getBodyRotateX() { return bodyX; }
        @Override public float getBodyRotateY() { return bodyY; }
        @Override public float getBodyRotateZ() { return bodyZ; }
        @Override public boolean overridesHead() { return overrideHead; }
        @Override public boolean overridesRightArm() { return overrideRightArm; }
        @Override public boolean overridesLeftArm() { return overrideLeftArm; }
        @Override public boolean overridesRightLeg() { return overrideRightLeg; }
        @Override public boolean overridesLeftLeg() { return overrideLeftLeg; }
        @Override public boolean overridesBody() { return overrideBody; }
    }
}
