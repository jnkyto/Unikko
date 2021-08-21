/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.cmd.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class GuiCommand extends Command {
    public GuiCommand() {
        super("gui", "Open GUI (doesn't work)");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {}
}
