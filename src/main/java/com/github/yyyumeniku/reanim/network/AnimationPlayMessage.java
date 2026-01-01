package com.github.yyyumeniku.reanim.network;

import com.github.yyyumeniku.reanim.api.AnimationAPI;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Packet sent from client to server to request playing an animation.
 * Used for player-initiated animations like emotes.
 */
public class AnimationPlayMessage implements IMessage {
    
    private String animationId;
    private boolean stop; // true = stop animation, false = play animation
    
    public AnimationPlayMessage() {}
    
    public AnimationPlayMessage(ResourceLocation animationId) {
        this.animationId = animationId != null ? animationId.toString() : "";
        this.stop = false;
    }
    
    public AnimationPlayMessage(boolean stop) {
        this.animationId = "";
        this.stop = stop;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        animationId = ByteBufUtils.readUTF8String(buf);
        stop = buf.readBoolean();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, animationId);
        buf.writeBoolean(stop);
    }
    
    public static class Handler implements IMessageHandler<AnimationPlayMessage, IMessage> {
        @Override
        public IMessage onMessage(AnimationPlayMessage msg, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServer().addScheduledTask(() -> {
                if (msg.stop) {
                    AnimationAPI.stop(player);
                } else if (!msg.animationId.isEmpty()) {
                    AnimationAPI.play(player, new ResourceLocation(msg.animationId));
                }
                
                // TODO: Broadcast to nearby players
            });
            return null;
        }
    }
}
