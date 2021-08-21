package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class HelpCommand extends Command {
    public HelpCommand()    {
        super("help", "Shows help");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx ->(commonHelp(ctx.getSource(), "commands.uhelp.line.")))
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
                        .executes(ctx -> commandHelp(ctx.getSource(), "say")));
    }
    public static int commonHelp(CommandSource source, String message)   {
        for(int i=0; i<8; i++)  {
            Text feedback = new TranslatableText(message + i);
            ChatInfoUtils.sendFeedback(feedback);
        }
        return SINGLE_SUCCESS;
    }

    public static int commandHelp(CommandSource source, String command)   {
        Text feedback = new TranslatableText("commands.uhelp.command." + command);
        ChatInfoUtils.sendFeedback(feedback);
        return SINGLE_SUCCESS;
    }
}
