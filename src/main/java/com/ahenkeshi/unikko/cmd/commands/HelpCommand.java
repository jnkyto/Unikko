package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.List;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class HelpCommand extends Command {
    public HelpCommand()    {
        super("help");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx ->(commonHelp(ctx.getSource())))
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
                        .executes(ctx -> commandHelp(ctx.getSource(), "say")))
                .then(literal("nbt")
                        .executes(ctx -> commandHelp(ctx.getSource(), "nbt")));
                // I might get rid of these, and just use those hover events instead.
    }
    public static int commonHelp(CommandSource source)   {
        Unikko.logger.info("Help command was used -> commonHelp");

        List<Command> commands = CommandHandler.getAll();
        BaseText commandTextList = new LiteralText("");
        Text help0 = new TranslatableText("commands.uhelp.line.0");
        Text help1 = new TranslatableText("commands.uhelp.line.1");

        for(int i = 0; i < commands.size(); i++)
        {
            Command cmd = commands.get(i);
            commandTextList.append(getCommandText(cmd));
            if(!(i == commands.size() - 1))    {
                commandTextList.append(", ");
            }
        }
        ChatInfoUtils.sendFeedback(help0);
        ChatInfoUtils.sendFeedback(commandTextList);
        ChatInfoUtils.sendFeedback(help1);
        return SINGLE_SUCCESS;
    }

    public static int commandHelp(CommandSource source, String command)   {
        Unikko.logger.info("Help command was used -> commandHelp");
        Text feedback = new TranslatableText("commands.uhelp.command." + command);
        ChatInfoUtils.sendFeedback(feedback);
        return SINGLE_SUCCESS;
    }

    public static Text getCommandText(Command cmd)   {
        Text info = new TranslatableText("commands.uhelp.line." + cmd.getName());
        BaseText finalCommand = new LiteralText(SoftConfigUtils.getString("cmdPrefix") + cmd.getName());
        finalCommand.setStyle(finalCommand.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, info)));
        return finalCommand.formatted(Formatting.YELLOW);
    }
}
