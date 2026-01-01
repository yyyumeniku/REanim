package com.github.yyyumeniku.reanim.network;

import com.github.yyyumeniku.reanim.Tags;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Network packet handler for REanim.
 * Handles animation synchronization between server and clients.
 */
public class PacketHandler {
    
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);
    
    private static int id = 0;
    
    public static void init() {
        // Server -> Client: Sync animation state
        INSTANCE.registerMessage(
            AnimationSyncMessage.Handler.class,
            AnimationSyncMessage.class,
            id++,
            Side.CLIENT
        );
        
        // Client -> Server: Request animation play (for things like emotes)
        INSTANCE.registerMessage(
            AnimationPlayMessage.Handler.class,
            AnimationPlayMessage.class,
            id++,
            Side.SERVER
        );
    }
}
