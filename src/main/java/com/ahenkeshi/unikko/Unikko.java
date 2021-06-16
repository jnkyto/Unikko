package com.ahenkeshi.unikko;

import com.ahenkeshi.unikko.cmd.*;
import com.ahenkeshi.unikko.utils.BoolUtils;
import com.ahenkeshi.unikko.utils.ConfigUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

import java.io.IOException;

public class Unikko implements ClientModInitializer, ModInitializer {

	public static final String MODID = "Unikko";
	public static final String VERSION = "1.0.0";
	public static final String REL_DATE = "2021/06/16";

	@Override
	public void onInitialize()	{
		try {
			ConfigUtils.createFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BoolUtils.update();

		System.out.println("Unikko: Successfully initialized!");
	}

	@Override
	public void onInitializeClient() {

	}

	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)	{
		FovCommand.register(dispatcher);
		GammaCommand.register(dispatcher);
		CredCommand.register(dispatcher);
		ToggleCommand.register(dispatcher);
		//GuiCommand.register(dispatcher);

		// if (MinecraftClient.getInstance().isIntegratedServerRunning())	{}
	}

}
