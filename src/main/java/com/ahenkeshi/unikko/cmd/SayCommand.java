package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.ahenkeshi.unikko.cmd.CommandManager.addCommand;
import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SayCommand {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        addCommand("say");
        System.out.println("Unikko: ;say command registered");

        dispatcher.register(literal("say")
                .then(argument("message", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            MessageArgumentType.MessageFormat msg = ctx.getArgument("message",
                                    MessageArgumentType.MessageFormat.class);
                            String msgString = msg.getContents();
                            mc.getNetworkHandler().sendPacket(new ChatMessageC2SPacket(msgString));
                            return SINGLE_SUCCESS;
                        })));
    }
}
