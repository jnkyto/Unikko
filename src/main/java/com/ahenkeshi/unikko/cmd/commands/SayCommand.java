package com.ahenkeshi.unikko.cmd.commands;

import com.ahenkeshi.unikko.cmd.Command;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.TranslatableText;

import java.util.Objects;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SayCommand extends Command {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public SayCommand() {
        super("say", "Sends a message to chat");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder)    {
        builder.executes(ctx ->(incomplete(ctx.getSource())))
                .then(argument("message", StringArgumentType.greedyString()).executes(ctx -> {
            String msg = ctx.getArgument("message", String.class);
            Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new ChatMessageC2SPacket(msg));
            return SINGLE_SUCCESS;
        }));
    }

    private int incomplete(CommandSource source)    {
        ChatInfoUtils.sendFeedback(new TranslatableText("commands.uhelp.command.say"));
        return SINGLE_SUCCESS;
    }
}
