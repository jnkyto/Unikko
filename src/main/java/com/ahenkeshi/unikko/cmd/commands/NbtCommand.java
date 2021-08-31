/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static com.ahenkeshi.unikko.Unikko.logger;

public class NbtCommand extends Command {
    public NbtCommand() {
        super("nbt");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(ctx -> {
            if(mc.player != null)   {
                Item item = mc.player.getStackInHand(Hand.MAIN_HAND).getItem();
                if(item.asItem() != null)   {
                    viewNbt(ctx.getSource(), item);
                }
            }
            return SINGLE_SUCCESS;
        });
    }

    private void viewNbt(CommandSource source, Item item)    {
        logger.info("Nbt command was used -> viewNbt");
        String itemNbtStr = "";
        String itemStr = item.getName().getString();
        Text feedback0 = new TranslatableText("commands.nbt.no_nbt.line.0");
        Text feedback1;
        if (mc.player != null) {
            NbtCompound itemNbt = mc.player.getStackInHand(Hand.MAIN_HAND).getNbt();
            if (itemNbt != null) {
                itemNbtStr += itemNbt.toString();
                feedback0 = new TranslatableText("commands.nbt.item", itemStr);
                feedback1 = Text.of(itemNbtStr);
                ChatInfoUtils.sendFeedback(feedback0);
                ChatInfoUtils.sendFeedback(feedback1);
            } else {
                ChatInfoUtils.sendFeedback(feedback0);
            }
        }
    }
}