package com.ahenkeshi.unikko;

import com.ahenkeshi.unikko.cmd.*;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.ahenkeshi.unikko.utils.HardConfigUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

import java.io.IOException;

public class Unikko implements ClientModInitializer, ModInitializer {
	public static final boolean DEV_MODE = true;

	public static final String MODID = "Unikko";
	public static String REL_DATE = "2021/08/19";
	public static String VERSION = "1.0.1";

	@Override
	public void onInitialize()	{
		try {HardConfigUtils.createFile();} catch (IOException e) {e.printStackTrace();}
		SoftConfigUtils.saveBooleansToConfigFile();

		if(DEV_MODE){VERSION += "-dev";} else{REL_DATE = "";}

		System.out.println("Unikko: what's the difference between onInitialize and onInitializeClient");
	}

	@Override
	public void onInitializeClient() {
		System.out.println("Unikko: i may never know");
	}

	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)	{
		FovCommand.register(dispatcher);
		GammaCommand.register(dispatcher);
		CredCommand.register(dispatcher);
		ToggleCommand.register(dispatcher);
		HelpCommand.register(dispatcher);
		SayCommand.register(dispatcher);
		//GuiCommand.register(dispatcher);

		// if (MinecraftClient.getInstance().isIntegratedServerRunning())	{}
	}

}
