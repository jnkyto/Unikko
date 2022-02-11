/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static com.mojang.brigadier.arguments.DoubleArgumentType.getDouble;

public class GammaCommand extends Command {
    public GammaCommand()   {
        super("gamma");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx ->(incomplete(ctx.getSource())))
                .then(argument("gamma", doubleArg())
                        .executes(ctx -> setGamma(ctx.getSource(), getDouble(ctx, "gamma"))))
                .then(literal("fb")
                        .executes(ctx -> setGamma(ctx.getSource(), 100)));
    }

    private int setGamma(CommandSource source, double newGamma)    {
        Unikko.LOGGER.info("Gamma command was used -> setGamma");
        MinecraftClient.getInstance().options.gamma = newGamma;
        Text feedback = new TranslatableText("commands.ugamma.success", newGamma);
        ChatInfoUtils.sendFeedback(feedback);
        return SINGLE_SUCCESS;
    }

    private int incomplete(CommandSource source) {
        Unikko.LOGGER.info("Gamma command was used -> incomplete");
        ChatInfoUtils.sendFeedback(new TranslatableText("commands.uhelp.command.gamma"));
        return SINGLE_SUCCESS;
    }

}
