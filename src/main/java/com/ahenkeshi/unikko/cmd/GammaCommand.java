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
import static com.mojang.brigadier.arguments.DoubleArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class GammaCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        addCommand("ugamma");
        System.out.println("Unikko: ugamma command registered.");

        dispatcher.register(literal("ugamma")
                .then(argument("gamma", doubleArg())
                    .executes(ctx -> setGamma(ctx.getSource(), getDouble(ctx, "gamma"))))
                .then(literal("FullBright")
                    .executes(ctx -> setGamma(ctx.getSource(), 100))));
    }

    private static int setGamma(ServerCommandSource source, double newGamma)    {
        System.out.println("Unikko: ugamma command was used");
        MinecraftClient.getInstance().options.gamma = newGamma;
        Text feedback = new TranslatableText("commands.ugamma.success", newGamma);
        sendFeedback(feedback);
        return 0;
    }

}
