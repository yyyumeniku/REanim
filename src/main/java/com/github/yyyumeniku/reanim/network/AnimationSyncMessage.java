package com.github.yyyumeniku.reanim.network;

import com.github.yyyumeniku.reanim.capability.AnimationProvider;
import com.github.yyyumeniku.reanim.capability.IAnimationCapability;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Packet sent from server to client to sync animation state.
 */
public class AnimationSyncMessage implements IMessage {
    
    private int entityId;
    private String animationId; // Empty string = no animation
    private int currentTick;
    
    public AnimationSyncMessage() {}
    
    public AnimationSyncMessage(int entityId, ResourceLocation animationId, int currentTick) {
        this.entityId = entityId;
        this.animationId = animationId != null ? animationId.toString() : "";
        this.currentTick = currentTick;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        animationId = ByteBufUtils.readUTF8String(buf);
        currentTick = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        ByteBufUtils.writeUTF8String(buf, animationId);
        buf.writeInt(currentTick);
    }
    
    public static class Handler implements IMessageHandler<AnimationSyncMessage, IMessage> {
        @Override
        public IMessage onMessage(AnimationSyncMessage msg, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity entity = Minecraft.getMinecraft().world.getEntityByID(msg.entityId);
                if (entity != null) {
                    IAnimationCapability cap = entity.getCapability(AnimationProvider.ANIMATION_CAP, null);
                    if (cap != null) {
                        if (msg.animationId.isEmpty()) {
                            cap.setCurrentAnimation(null);
                        } else {
                            cap.setCurrentAnimation(new ResourceLocation(msg.animationId));
                            cap.setCurrentTick(msg.currentTick);
                        }
                    }
                }
            });
            return null;
        }
    }
}
