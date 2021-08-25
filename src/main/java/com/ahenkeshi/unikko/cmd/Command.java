/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
     protected static MinecraftClient mc;

     private final String name;
     private static List<String> commands = new ArrayList<>();

     public Command(String name)   {
         this.name = name;
         mc = MinecraftClient.getInstance();
         commands.add(name);
     }

     protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(final String name, final ArgumentType<T> type) {
         return RequiredArgumentBuilder.argument(name,type);
     }

     protected static LiteralArgumentBuilder<CommandSource> literal(final String name)  {
         return LiteralArgumentBuilder.literal(name);
     }

     public final void registerTo(CommandDispatcher<CommandSource> dispatcher)  {
         register(dispatcher, name);
     }

     public void register(CommandDispatcher<CommandSource> dispatcher, String name) {
         LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
         build(builder);
         dispatcher.register(builder);
     }

     public abstract void build(LiteralArgumentBuilder<CommandSource> builder);

     public String getName()    {
         return name;
     }

     public static boolean isCommand(String command)  {
         return commands.stream().anyMatch(command::equals);
     }
}
