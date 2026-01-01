package com.github.yyyumeniku.reanim.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.yyyumeniku.reanim.api.AnimationAPI;
import com.github.yyyumeniku.reanim.api.IAnimationController;
import com.github.yyyumeniku.reanim.api.IPose;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * Mixin to hook into ModelBiped's setRotationAngles method.
 * This allows us to override vanilla pose with our custom animation pose.
 */
@Mixin(ModelBiped.class)
public abstract class MixinModelBiped {
    
    @Shadow public ModelRenderer bipedHead;
    @Shadow public ModelRenderer bipedHeadwear;
    @Shadow public ModelRenderer bipedBody;
    @Shadow public ModelRenderer bipedRightArm;
    @Shadow public ModelRenderer bipedLeftArm;
    @Shadow public ModelRenderer bipedRightLeg;
    @Shadow public ModelRenderer bipedLeftLeg;
    
    /**
     * Inject at the END of setRotationAngles to override vanilla pose
     * with our custom animation pose.
     */
    @Inject(
        method = "setRotationAngles",
        at = @At("TAIL")
    )
    private void reanim$applyCustomPose(
            float limbSwing, 
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            float scaleFactor,
            Entity entityIn,
            CallbackInfo ci) {
        
        if (!(entityIn instanceof EntityLivingBase)) {
            return;
        }
        
        EntityLivingBase living = (EntityLivingBase) entityIn;
        IAnimationController controller = AnimationAPI.getController(living);
        
        if (controller == null || !controller.isPlaying()) {
            return;
        }
        
        float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        IPose pose = controller.getCurrentPose(partialTicks);
        
        if (pose == null) {
            return;
        }
        
        // Apply pose to model
        if (pose.overridesHead()) {
            bipedHead.rotateAngleX = pose.getHeadRotateX();
            bipedHead.rotateAngleY = pose.getHeadRotateY();
            bipedHead.rotateAngleZ = pose.getHeadRotateZ();
            // Copy to headwear
            bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
            bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
            bipedHeadwear.rotateAngleZ = bipedHead.rotateAngleZ;
        }
        
        if (pose.overridesRightArm()) {
            bipedRightArm.rotateAngleX = pose.getRightArmRotateX();
            bipedRightArm.rotateAngleY = pose.getRightArmRotateY();
            bipedRightArm.rotateAngleZ = pose.getRightArmRotateZ();
        }
        
        if (pose.overridesLeftArm()) {
            bipedLeftArm.rotateAngleX = pose.getLeftArmRotateX();
            bipedLeftArm.rotateAngleY = pose.getLeftArmRotateY();
            bipedLeftArm.rotateAngleZ = pose.getLeftArmRotateZ();
        }
        
        if (pose.overridesRightLeg()) {
            bipedRightLeg.rotateAngleX = pose.getRightLegRotateX();
            bipedRightLeg.rotateAngleY = pose.getRightLegRotateY();
            bipedRightLeg.rotateAngleZ = pose.getRightLegRotateZ();
        }
        
        if (pose.overridesLeftLeg()) {
            bipedLeftLeg.rotateAngleX = pose.getLeftLegRotateX();
            bipedLeftLeg.rotateAngleY = pose.getLeftLegRotateY();
            bipedLeftLeg.rotateAngleZ = pose.getLeftLegRotateZ();
        }
        
        if (pose.overridesBody()) {
            bipedBody.rotateAngleX = pose.getBodyRotateX();
            bipedBody.rotateAngleY = pose.getBodyRotateY();
            bipedBody.rotateAngleZ = pose.getBodyRotateZ();
        }
    }
}
