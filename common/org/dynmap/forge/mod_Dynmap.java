package org.dynmap.forge;

import java.io.IOException;
import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "Dynmap", name = "Dynmap", version = Version.VER)
public class mod_Dynmap
{
    // The instance of your mod that Forge uses.
    @Instance("Dynmap")
    public static mod_Dynmap instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "org.dynmap.forge.ClientProxy", serverSide = "org.dynmap.forge.Proxy")
    public static Proxy proxy;
    
    public static DynmapPlugin plugin;

    public class LoadingCallback implements net.minecraftforge.common.ForgeChunkManager.LoadingCallback {
        @Override
        public void ticketsLoaded(List<Ticket> tickets, World world) {
            if(tickets.size() > 0) {
                DynmapPlugin.setBusy(world, tickets.get(0));
                for(int i = 1; i < tickets.size(); i++) {
                    ForgeChunkManager.releaseTicket(tickets.get(i));
                }
            }
        }
    }

    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    @Init
    public void load(FMLInitializationEvent event)
    {
        /* Set up for chunk loading notice from chunk manager */
        ForgeChunkManager.setForcedChunkLoadingCallback(mod_Dynmap.instance, new LoadingCallback());
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    @ServerStarted
    public void serverStarted(FMLServerStartedEvent event)
    {
    	plugin = proxy.startServer();
    }
    @ServerStopping
    public void serverStopping(FMLServerStoppingEvent event)
    {
    	proxy.stopServer(plugin);
    }
}
