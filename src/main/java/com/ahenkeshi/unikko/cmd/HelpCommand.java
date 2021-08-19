package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.ahenkeshi.unikko.cmd.CommandManager.addCommand;
import static com.ahenkeshi.unikko.cmd.CommandManager.sendFeedback;
import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static net.minecraft.server.command.CommandManager.literal;

public class HelpCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)  {
        addCommand("help");
        System.out.println("Unikko: ;help command registered");

        dispatcher.register(literal("help").executes(ctx ->
                commonHelp(ctx.getSource(), "commands.uhelp.line."))
                .then(literal("fov")
                        .executes(ctx -> commandHelp(ctx.getSource(), "fov")))
                .then(literal("gamma")
                        .executes(ctx -> commandHelp(ctx.getSource(), "gamma")))
                .then(literal("toggle")
                        .executes(ctx -> commandHelp(ctx.getSource(), "toggle")))
                .then(literal("help")
                        .executes(ctx -> commandHelp(ctx.getSource(), "help")))
                .then(literal("cred")
                        .executes(ctx -> commandHelp(ctx.getSource(), "cred")))
                .then(literal("say")
                        .executes(ctx -> commandHelp(ctx.getSource(), "say"))));
    }

    public static int commonHelp(ServerCommandSource source, String message)   {
        for(int i=0; i<7; i++)  {
            Text feedback = new TranslatableText(message + i);
            sendFeedback(feedback);
        }
        return SINGLE_SUCCESS;
    }

    public static int commandHelp(ServerCommandSource source, String command)   {
        Text feedback = new TranslatableText("commands.uhelp.command." + command);
        sendFeedback(feedback);
        return SINGLE_SUCCESS;
    }
}
