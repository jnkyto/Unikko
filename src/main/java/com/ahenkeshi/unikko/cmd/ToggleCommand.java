/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd;

import com.ahenkeshi.unikko.modules.DiscordRPC;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.ahenkeshi.unikko.cmd.CommandManager.*;
import static net.minecraft.server.command.CommandManager.*;

public class ToggleCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)  {
        addCommand("utoggle");
        System.out.println("Unikko: utoggle command registered");

        dispatcher.register(literal("utoggle")
                .then(literal("hudrender")
                        .executes(ctx -> toggle(ctx.getSource(), "hudRender")))
                .then(literal("discordrpc")
                        .executes(ctx -> toggle(ctx.getSource(), "discordRpc"))));
    }

    private static int toggle(ServerCommandSource source, String setting)   {
        String bool = String.valueOf(SoftConfigUtils.get(setting));
        if(bool.equals("true")) {
            bool = "false";
        } else  {
            bool = "true";
        }
        SoftConfigUtils.updateBoolean(setting, bool);
        Text feedback = new TranslatableText("commands.utoggle." + setting + ".success", bool);
        sendFeedback(feedback);
        SoftConfigUtils.saveBooleansToConfigFile();
        if(setting.equals("discordRpc")) {
            DiscordRPC.init();
        }
        return 0;
    }
}
