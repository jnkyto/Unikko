/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd;

import static com.ahenkeshi.unikko.cmd.CommandManager.*;
import com.ahenkeshi.unikko.gui.UnikkoGui;
import com.ahenkeshi.unikko.gui.UnikkoScreen;
import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.cotton.gui.GuiDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.*;

public class GuiCommand {
    private static GuiDescription desc;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)  {
        addCommand("ugui");
        System.out.println("Unikko: ugui command registered.");

        dispatcher.register(literal("ugui").executes(ctx ->
                openGUI(ctx.getSource())));
    }

    private static int openGUI(ServerCommandSource source)  {
        /*System.out.println("Unikko: Gui toggled");
        MinecraftClient.getInstance().openScreen(new UnikkoScreen(new UnikkoGui()));*/
        return 0;
    }
}
