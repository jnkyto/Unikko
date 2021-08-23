/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.modules.DiscordRPC;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ToggleCommand extends Command {
    public ToggleCommand()  {
        super("toggle", "Toggle the state of a module");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx ->(incomplete(ctx.getSource())))
                .then(literal("hud")
                    .executes(ctx -> toggle(ctx.getSource(), "hudRender")))
                .then(literal("rpc")
                        .executes(ctx -> toggle(ctx.getSource(), "discordRpc")));
    }

    private static int toggle(CommandSource source, String setting)   {
        System.out.println("Unikko: toggle command was used");
        Boolean bool = SoftConfigUtils.getBoolean(setting);
        bool = !bool;
        SoftConfigUtils.updateBoolean(setting, String.valueOf(bool));
        Text feedback = new TranslatableText("commands.utoggle." + setting + ".success", bool);
        ChatInfoUtils.sendFeedback(feedback);
        SoftConfigUtils.saveBooleansToConfigFile();
        if(setting.equals("discordRpc")) {
            DiscordRPC.init();
        }
        return SINGLE_SUCCESS;
    }

    private static int incomplete(CommandSource source) {
        System.out.println("Unikko: toggle command was used -> incomplete");
        ChatInfoUtils.sendFeedback(new TranslatableText("commands.uhelp.command.toggle"));
        return SINGLE_SUCCESS;
    }
}
