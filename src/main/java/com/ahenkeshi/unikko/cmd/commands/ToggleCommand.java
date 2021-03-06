/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.ahenkeshi.unikko.utils.config.SoftConfig;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ToggleCommand extends Command {
    public ToggleCommand()  {
        super("toggle");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx ->(incomplete(ctx.getSource())))
                .then(literal("hud")
                    .executes(ctx -> toggle(Unikko.SOFTCONFIG.hudRender)))
                .then(literal("rpc")
                        .executes(ctx -> toggle(Unikko.SOFTCONFIG.discordRpc))
                .then(literal("detailed")
                        .executes(ctx -> toggle(Unikko.SOFTCONFIG.rpcAll))));
    }

    private int toggle(SoftConfig.SoftConfigEntry<Boolean> setting)   {
        boolean temp = !(Boolean) setting.value();
        Unikko.LOGGER.info("Toggle command was used, setting:" + setting.key() + " old:" + setting.value() + " new:" + temp);
        setting.set(temp);
        Text feedback = new TranslatableText("commands.utoggle." + setting.key() + ".success", setting.value());
        ChatInfoUtils.sendFeedback(feedback);
        Unikko.SOFTCONFIG.pushHard();
        return SINGLE_SUCCESS;
    }

    private int incomplete(CommandSource source) {
        Unikko.LOGGER.info("Toggle command was used -> incomplete");
        ChatInfoUtils.sendFeedback(new TranslatableText("commands.uhelp.command.toggle"));
        return SINGLE_SUCCESS;
    }
}
