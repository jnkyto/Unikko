package com.ahenkeshi.unikko.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;

public abstract class Command {
     protected static MinecraftClient mc;

     private final String name;
     private final String desc;

     public Command(String name, String desc)   {
         this.name = name;
         this.desc = desc;
         mc = MinecraftClient.getInstance();
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

     public String getDesc()    {
         return desc;
     }
}
