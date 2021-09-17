/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode

Unikko's new (meaning this one) command framework was ***heavily*** inspired by Meteor client.
*/

package com.ahenkeshi.unikko.cmd;

import com.ahenkeshi.unikko.cmd.commands.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ahenkeshi.unikko.cmd.commands.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static final CommandDispatcher<CommandSource> DISPATCHER = new CommandDispatcher<>();
    private static final List<Command> commands = new ArrayList<>();
    private final static CommandSource COMMAND_SOURCE = new ChatCommandSource(mc);
    private static final Map<Class<? extends Command>, Command> commandMap = new HashMap<>();

    public static Class<CommandHandler> get() {
        return CommandHandler.class;
    }

    public static void init()  {
        add(new CredCommand());
        add(new FovCommand());
        add(new GammaCommand());
        add(new SayCommand());
        add(new HelpCommand());
        add(new ToggleCommand());
        add(new NbtCommand());
    }

    public static void dispatch(String message) throws CommandSyntaxException  {
        dispatch(message, new ChatCommandSource(mc));
    }

    public static void dispatch(String message, CommandSource source) throws CommandSyntaxException    {
        ParseResults<CommandSource> res = DISPATCHER.parse(message, source);
        DISPATCHER.execute(res);
    }

    public static CommandDispatcher<CommandSource> getDISPATCHER() {
        return DISPATCHER;
    }

    public static CommandSource getCOMMAND_SOURCE()    {
        return COMMAND_SOURCE;
    }

    private final static class ChatCommandSource extends ClientCommandSource    {
        public ChatCommandSource(MinecraftClient mc)    {
            super(null, mc);
        }
    }

    public static void add(Command command)    {
        commands.removeIf(command1 -> command1.getName().equals(command.getName()));
        commandMap.values().removeIf(command1 -> command1.getName().equals(command.getName()));

        command.registerTo(DISPATCHER);
        commands.add(command);
        commandMap.put(command.getClass(), command);
    }

    public int getCount()   {
        return commands.size();
    }

    public static List<Command> getAll()   {
        return commands;
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> T get(Class<T> clazz)    {
        return (T) commandMap.get(clazz);
    }
}
