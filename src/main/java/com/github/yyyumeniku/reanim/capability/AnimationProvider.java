package com.github.yyyumeniku.reanim.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Capability provider for animation capability.
 */
public class AnimationProvider implements ICapabilitySerializable<NBTTagCompound> {
    
    @CapabilityInject(IAnimationCapability.class)
    public static Capability<IAnimationCapability> ANIMATION_CAP = null;
    
    private final IAnimationCapability instance;
    
    public AnimationProvider() {
        this.instance = new AnimationCapability();
    }
    
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ANIMATION_CAP;
    }
    
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == ANIMATION_CAP) {
            return ANIMATION_CAP.cast(instance);
        }
        return null;
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) ANIMATION_CAP.getStorage().writeNBT(ANIMATION_CAP, instance, null);
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        ANIMATION_CAP.getStorage().readNBT(ANIMATION_CAP, instance, null, nbt);
    }
}
