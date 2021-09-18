/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class CredCommand extends Command {
    public CredCommand()    {
        super("cred");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx -> credits(ctx.getSource()));
    }

    private static int credits(CommandSource source)  {
        Unikko.logger.info("Cred command was used");
        BaseText github = new LiteralText("jnkyto/Unikko");
        github.setStyle(github.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                "https://github.com/jnkyto/Unikko"))).formatted(Formatting.GREEN, Formatting.UNDERLINE);
        for(int i=0; i<4; i++) {
            BaseText feedback = new TranslatableText("commands.ucred.line." + i);
            if(i == 3)  {feedback.append(github);}
            ChatInfoUtils.sendFeedback(feedback);
        }
        return SINGLE_SUCCESS;
    }
}
