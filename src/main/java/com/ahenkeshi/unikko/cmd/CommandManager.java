/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.StringReader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandException;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;

public class CommandManager {
    private static final Set<String> commands = new HashSet<>();

    public static void clearCommands()  {
        commands.clear();
    }

    public static void addCommand(String name)    {
        commands.add(name);
    }

    public static boolean isCommand(String name)   {
        return commands.contains(name);
    }

    /*public static boolean getFlag(ServerCommandSource source, int flag)    {
        return (((IServerCommandSource) source).getLevel() & flag != 0);
    }*/

    public static void sendFeedback(Text message)   {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(message);
    }

    public static void sendError(Text error) {
        sendFeedback(new LiteralText("").append(error).formatted(Formatting.RED));
    }

    public static void executeCommand(StringReader reader, String command)  {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        try {
            assert player != null;
            player.networkHandler.getCommandDispatcher().execute(reader, new FakeCommandSource(player));
        } catch (CommandException e)    {
            CommandManager.sendError(e.getTextMessage());
        } catch (CommandSyntaxException e)  {
            CommandManager.sendError(Texts.toText(e.getRawMessage()));
            if (e.getInput() != null && e.getCursor() >= 0) {
                int cursor = Math.min(e.getCursor(), e.getInput().length());
                MutableText text = new LiteralText("").formatted(Formatting.GRAY)
                        .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)));
                if (cursor > 10)
                    text.append("...");

                text.append(e.getInput().substring(Math.max(0, cursor - 10), cursor));
                if (cursor < e.getInput().length()) {
                    text.append(new LiteralText(e.getInput().substring(cursor)).formatted(Formatting.RED, Formatting.UNDERLINE));
                }

                text.append(new TranslatableText("command.context.here").formatted(Formatting.RED, Formatting.ITALIC));
                CommandManager.sendError(text);
            }
        } catch (Exception e) {
            LiteralText error = new LiteralText(e.getMessage() == null ? e.getClass().getName() : e.getMessage());
            CommandManager.sendError(new TranslatableText("command.failed")
                    .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, error))));
            e.printStackTrace();
        }
    }
}

