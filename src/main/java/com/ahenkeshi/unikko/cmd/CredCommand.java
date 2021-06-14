package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.ahenkeshi.unikko.cmd.CommandManager.*;
import static net.minecraft.server.command.CommandManager.*;

public class CredCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)  {
        addCommand("ucred");
        System.out.println("Unikko: ucred command registered");

        dispatcher.register(literal("ucred").executes(ctx ->
                credits(ctx.getSource(), "commands.ucred.line.")));
    }

    private static int credits(ServerCommandSource source, String message)  {
        for(int i=0; i<4; i++) {
            Text feedback = new TranslatableText(message + i);
            sendFeedback(feedback);
        }
        return 0;
    }
}
