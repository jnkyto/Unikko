/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.ahenkeshi.unikko.cmd.CommandManager.*;
import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static net.minecraft.server.command.CommandManager.*;


public class CredCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)  {
        addCommand("cred");
        System.out.println("Unikko: ;cred command registered");

        dispatcher.register(literal("cred").executes(ctx ->
                credits(ctx.getSource(), "commands.ucred.line.")));
    }

    private static int credits(ServerCommandSource source, String message)  {
        for(int i=0; i<4; i++) {
            Text feedback = new TranslatableText(message + i);
            sendFeedback(feedback);
        }
        return SINGLE_SUCCESS;
    }
}
