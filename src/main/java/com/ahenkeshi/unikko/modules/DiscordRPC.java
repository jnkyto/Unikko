/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode

Inspired by Amitoj Singh's Amitojs-Minecraft-RPC-Fabric
*/

package com.ahenkeshi.unikko.modules;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.ahenkeshi.unikko.Unikko;
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

    private static Timer t = new Timer();

    public static void init() {
        handlers.ready = (user) -> Unikko.LOGGER.info("DiscordRPC initialized.");
        lib.Discord_Initialize(applicationID, handlers, true, steamId);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}}}, "RPCCallbackHandler").start();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updatePresence();
            }
        };
        t.scheduleAtFixedRate(task, 5000, 5000);
        Unikko.LOGGER.info("Started or updated rich presence.");
    }

    private static void killRpc()   {
        lib.Discord_ClearPresence();
    }

    /*
    DiscordRPCModule cheat sheet:
    Line 1 Title: Game title from app id
    Line 2 presence.Details: Version ( + server / singleplayer )
    Line 3 presence.State: Main menu / Holding
    Line 4: Playtime
     */


    private static DiscordRichPresence basicPresence() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = start_time; // epoch second
        presence.largeImageKey = "icon";
        presence.largeImageText = Unikko.MOD.getMetadata().getVersion().getFriendlyString();
        presence.details = "ウニッコは最高!!";
        // presence.state = "keep this here in case I want to do something w/ it";
        presence.instance = 1;
        lib.Discord_UpdatePresence(presence);
        return presence;
    }

    private static void updatePresence() {
        boolean shouldStart = Unikko.SOFTCONFIG.discordRpc.value();
        boolean rpcAll = Unikko.SOFTCONFIG.rpcAll.value();
        if (!shouldStart)   {
            killRpc();
        } else if (!rpcAll) {
            basicPresence();
        } else if (mc.world != null) {
            DiscordRichPresence presence = basicPresence();
            boolean inSingleplayer = mc.isInSingleplayer();
            String playername = mc.getSession().getUsername();
            if (mc.player != null){
                ItemStack held_item = mc.player.getStackInHand(Hand.MAIN_HAND);
                String item_name = held_item.getName().getString();
                if (!item_name.equals("Air")) {
                    presence.state = "Holding " + item_name;
                } else {
                    presence.state = "Punching the air";
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
                }
                presence.details = "Playing at " + serverip;
            } else {
                presence.details = "Singleplayer";
            }
            presence.smallImageText = "Playing as " + playername;
            lib.Discord_UpdatePresence(presence);
        } else {
            basicPresence();
        }
    }
}
