/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.ahenkeshi.unikko.cmd.CommandManager.*;
import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static com.mojang.brigadier.arguments.DoubleArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class FovCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)  {
        addCommand("fov");
        System.out.println("Unikko: ;fov command registered.");

        dispatcher.register(literal("fov")
                .then(argument("fov", doubleArg())
                    .executes(ctx -> setFov(ctx.getSource(), getDouble(ctx, "fov"))))
                .then(literal("normal")
                        .executes(ctx -> setFov(ctx.getSource(), 70)))
                .then(literal("quake")
                        .executes(ctx -> setFov(ctx.getSource(), 110))));
        }

    private static int setFov(ServerCommandSource source, double newFov) {
        System.out.println("Unikko: ufov command was used");
        MinecraftClient.getInstance().options.fov = newFov;
        Text feedback = new TranslatableText("commands.ufov.success", newFov);
        sendFeedback(feedback);

        return SINGLE_SUCCESS;
    }

}
