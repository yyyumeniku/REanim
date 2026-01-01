package com.github.yyyumeniku.reanim.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * NBT storage handler for animation capability.
 * Saves/loads animation state to/from NBT.
 */
public class AnimationStorage implements Capability.IStorage<IAnimationCapability> {
    
    @Override
    @Nullable
    public NBTBase writeNBT(Capability<IAnimationCapability> capability, IAnimationCapability instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        
        ResourceLocation currentAnim = instance.getCurrentAnimationId();
        if (currentAnim != null) {
            nbt.setString("animation", currentAnim.toString());
            nbt.setInteger("tick", instance.getCurrentTick());
        }
        
        return nbt;
    }
    
    @Override
    public void readNBT(Capability<IAnimationCapability> capability, IAnimationCapability instance, EnumFacing side, NBTBase nbtBase) {
        if (!(nbtBase instanceof NBTTagCompound)) return;
        
        NBTTagCompound nbt = (NBTTagCompound) nbtBase;
        
        if (nbt.hasKey("animation")) {
            String animStr = nbt.getString("animation");
            ResourceLocation animId = new ResourceLocation(animStr);
            instance.setCurrentAnimation(animId);
            instance.setCurrentTick(nbt.getInteger("tick"));
        }
    }
}
