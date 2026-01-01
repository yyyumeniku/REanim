package com.github.yyyumeniku.reanim;

import com.github.yyyumeniku.reanim.capability.AnimationCapability;
import com.github.yyyumeniku.reanim.capability.AnimationProvider;
import com.github.yyyumeniku.reanim.capability.IAnimationCapability;
import com.github.yyyumeniku.reanim.network.PacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * REanim - Lightweight Animation Library for Minecraft 1.12.2
 * Uses Mixin to hook into rendering for custom player/entity poses.
 */
@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class REanim {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
    private static final ResourceLocation ANIMATION_CAP_ID = new ResourceLocation(Tags.MOD_ID, "animation");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Register capability
        CapabilityManager.INSTANCE.register(
            IAnimationCapability.class,
            new com.github.yyyumeniku.reanim.capability.AnimationStorage(),
            AnimationCapability::new
        );
        
        // Register network
        PacketHandler.init();
        
        // Register events
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        // Only attach to players for now (most common use case, lower overhead)
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(ANIMATION_CAP_ID, new AnimationProvider());
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        
        // Update animation controller each tick
        IAnimationCapability cap = event.player.getCapability(AnimationProvider.ANIMATION_CAP, null);
        if (cap != null) {
            cap.tick();
        }
    }
}
