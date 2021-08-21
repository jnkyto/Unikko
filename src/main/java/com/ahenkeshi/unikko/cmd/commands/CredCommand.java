/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class CredCommand extends Command {
    public CredCommand()    {
        super("cred", "Show credits");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx -> credits(ctx.getSource(), "commands.ucred.line."));
    }

    private static int credits(CommandSource source, String message)  {
        for(int i=0; i<4; i++) {
            Text feedback = new TranslatableText(message + i);
            ChatInfoUtils.sendFeedback(feedback);
        }
        return SINGLE_SUCCESS;
    }
}
