/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.modules;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.Timer;
import java.util.TimerTask;

public class DiscordRPC {
    private static final club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
    private static final String applicationID = "851423491525705769";
    private static final String steamId = "";
    private static final DiscordEventHandlers handlers = new DiscordEventHandlers();
    private static final Long start_time = System.currentTimeMillis() / 1000;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean rpcAll;

    private static Integer times = 0;
    private static Timer t = new Timer();
    private static TimerTask task;

    public static void init() {
        rpcAll = SoftConfigUtils.getBoolean("rpcAll");
        handlers.ready = (user) -> Unikko.logger.info("DiscordRPC ready.");
        lib.Discord_Initialize(applicationID, handlers, true, steamId);
        boolean shouldStart = SoftConfigUtils.getBoolean("discordRpc");

        if (shouldStart) {
            basicPresence();
            new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    lib.Discord_RunCallbacks();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {}
                }
            }, "RPCCallbackHandler").start();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    updatePresence();
                }
            };
            t.scheduleAtFixedRate(task, 5000, 5000);
            Unikko.logger.info("Started or updated rich presence.");
        } else {
            lib.Discord_ClearPresence();
            lib.Discord_Shutdown();
            if(task != null)    {
                task.cancel();
                t.purge();
            }
            Unikko.logger.info("DRPC init skipped because discordRpc is toggled false");
        }
    }

    /*
    DiscordRPCModule cheat sheet:
    Line 1 Title: Game title from app id
    Line 2 presence.Details: Version ( + server / singleplayer )
    Line 3 presence.State: Main menu / Holding
    Line 4: Playtime
     */


    public static void basicPresence() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = start_time; // epoch second
        presence.largeImageKey = "icon";
        presence.largeImageText = Unikko.MOD.getMetadata().getVersion().getFriendlyString();
        presence.details = Unikko.VERSION + Unikko.DEV;
        presence.state = "Playing with power!";
        presence.instance = 1;
        lib.Discord_UpdatePresence(presence);
    }

    private static void updatePresence() {

        if (mc.world != null & rpcAll) {
            times++;
            boolean inSingleplayer = mc.isInSingleplayer();
            String playername = mc.getSession().getUsername();
            DiscordRichPresence presence = new DiscordRichPresence();
            if(mc.player!=null){
                ItemStack held_item = mc.player.getStackInHand(Hand.MAIN_HAND);
                String item_name = held_item.getName().getString();
                if (!item_name.equals("Air")) {
                    presence.state = "Holding " + item_name;
                } else {
                    presence.state = "Epstori on top!";
                }
            }
            presence.startTimestamp = start_time;
            presence.largeImageKey = "icon";
            presence.largeImageText = Unikko.MOD.getMetadata().getVersion().getFriendlyString();
            presence.instance = 1;
            presence.smallImageKey = "player";
            if (!inSingleplayer) {
                String serverip = "";
                if (mc.getCurrentServerEntry() != null) {
                    serverip = mc.getCurrentServerEntry().address.toLowerCase();
                    if (serverip.equals("motimaa.net"))	{	// funny joek xd
                        playername = "Someone...";
                        serverip = "Somewhere...";
                    }
                }
                presence.details = Unikko.VERSION + Unikko.DEV + " | " + serverip;
            } else {
                presence.details = Unikko.VERSION + Unikko.DEV + " | Singleplayer";
            }
            presence.smallImageText = "Playing as " + playername;
            lib.Discord_UpdatePresence(presence);

        } else {
            basicPresence();
        }
    }
}
