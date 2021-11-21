/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.modules.DiscordRPC;
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
                    .executes(ctx -> toggle(Unikko.softConfig.hudRender)))
                .then(literal("rpc")
                        .executes(ctx -> toggle(Unikko.softConfig.discordRpc))
                    .then(literal("detailed")
                            .executes(ctx -> toggle(Unikko.softConfig.rpcAll))));
    }

    private static int toggle(SoftConfig.SoftConfigEntry setting)   {
        boolean temp = !(Boolean) setting.value();
        Unikko.logger.info("Toggle command was used, setting:" + setting.key() + " oval:" + setting.value() + " nval:" + temp);
        setting.set(temp);
        Text feedback = new TranslatableText("commands.utoggle." + setting.key() + ".success", setting.value());
        ChatInfoUtils.sendFeedback(feedback);
        if(setting.key().equals("discordRpc")) { /* this is stupid, maybe do some dRPC refacts for 1.2.0 or w/e */
            DiscordRPC.init();
        }
        Unikko.softConfig.pushHard();
        return SINGLE_SUCCESS;
    }

    private static int incomplete(CommandSource source) {
        Unikko.logger.info("Toggle command was used -> incomplete");
        ChatInfoUtils.sendFeedback(new TranslatableText("commands.uhelp.command.toggle"));
        return SINGLE_SUCCESS;
    }
}
