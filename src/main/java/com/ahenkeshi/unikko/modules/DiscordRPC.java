package com.ahenkeshi.unikko.modules;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.utils.BoolUtils;
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

    private static Integer times = 0;
    private static Timer t = new Timer();
    private static TimerTask task;

    public static void init() {
        handlers.ready = (user) -> System.out.println("Unikko: DiscordRPC ready.");
        lib.Discord_Initialize(applicationID, handlers, true, steamId);
        boolean shouldStart = BoolUtils.get("discordRpc");

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
            System.out.println("Unikko: DiscordRPC successfully started!");
        } else {
            lib.Discord_ClearPresence();
            lib.Discord_Shutdown();
            if(task != null)    {
                task.cancel();
                t.purge();
            }
            System.out.println("Unikko: DRPC init skipped because discordRpc is toggled false");
        }
    }

    /*
    DiscordRPCModule cheat sheet:
    Line 1 Title: Game title from app id
    Line 2 presence.Details: Version ( + server )
    Line 3 presence.State: Main menu / Holding
    Line 4: Playtime
     */

    public static void basicPresence() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = start_time; // epoch second
        presence.largeImageKey = "icon";
        presence.largeImageText = Unikko.MODID;
        presence.details = Unikko.VERSION;
        presence.state = "In the main menu";
        presence.instance = 1;
        lib.Discord_UpdatePresence(presence);
    }

    private static void updatePresence() {
        if (mc.world != null) {
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
            presence.largeImageText = Unikko.MODID;
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
                presence.details = Unikko.VERSION + " | " + serverip;
            } else {
                presence.details = Unikko.VERSION + " | Singleplayer";
            }
            presence.smallImageText = "Playing as " + playername;
            lib.Discord_UpdatePresence(presence);

        } else {
            basicPresence();
        }
    }
}
