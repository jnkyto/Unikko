package com.ahenkeshi.unikko;

import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.HardConfigUtils;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;

public class Unikko implements ClientModInitializer, ModInitializer {
	public static final boolean DEV_MODE = false;

	public static final String MODID = "Unikko";
	public static final String VERSION = "1.0.1";
	public static String REL_DATE = "2021/08/25";
	public static String DEV = "-dev";

	@Override
	public void onInitialize()	{
		try {HardConfigUtils.createFile();} catch (IOException e) {e.printStackTrace();}
		SoftConfigUtils.saveBooleansToConfigFile();

		if(!DEV_MODE){
			DEV = "";
			REL_DATE = "";
		}

		System.out.println("Unikko: what's the difference between onInitialize and onInitializeClient");
		CommandHandler.init();
	}

	@Override
	public void onInitializeClient() {}

}
