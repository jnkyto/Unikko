/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
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
        builder.executes(ctx ->(commonHelp(ctx.getSource())));
    }
    private int commonHelp(CommandSource source)   {
        Unikko.LOGGER.info("Help command was used -> commonHelp");

        List<Command> commands = CommandHandler.getAll();
        BaseText commandTextList = new LiteralText("");
        Text help0 = new TranslatableText("commands.uhelp.line.0");
        Text help1 = new TranslatableText("commands.uhelp.line.1");
        for (Command cmd : commands) {
            commandTextList.append(getCommandText(cmd));
            commandTextList.append(" ");
        }
        ChatInfoUtils.sendFeedback(help0);
        ChatInfoUtils.sendFeedback(commandTextList);
        ChatInfoUtils.sendFeedback(help1);
        return SINGLE_SUCCESS;
    }

    private Text getCommandText(Command cmd)   {
        BaseText info = new TranslatableText("commands.uhelp.command." + cmd.getName());
        BaseText finalCommand = new LiteralText(Unikko.SOFTCONFIG.cmdPrefix.value() + cmd.getName());
        finalCommand.setStyle(finalCommand.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, info)));
        return finalCommand.formatted(Formatting.RED);
    }
}
