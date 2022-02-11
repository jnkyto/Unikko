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

public class FovCommand extends Command {
    public FovCommand() {
        super("fov");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx ->(incomplete(ctx.getSource())))
                .then(argument("fov", doubleArg())
                .executes(ctx -> setFov(ctx.getSource(), getDouble(ctx, "fov"))))
                .then(literal("normal")
                        .executes(ctx -> setFov(ctx.getSource(), 70)))
                .then(literal("quake")
                        .executes(ctx -> setFov(ctx.getSource(), 110)));
    }
    private int setFov(CommandSource source, double newFov) {
        Unikko.LOGGER.info("Fov command was used -> setFov");
        MinecraftClient.getInstance().options.fov = newFov;
        Text feedback = new TranslatableText("commands.ufov.success", newFov);
        ChatInfoUtils.sendFeedback(feedback);
        return SINGLE_SUCCESS;
    }

    private int incomplete(CommandSource source) {
        Unikko.LOGGER.info("Fov command was used -> incomplete");
        ChatInfoUtils.sendFeedback(new TranslatableText("commands.uhelp.command.fov"));
        return SINGLE_SUCCESS;
    }
}
