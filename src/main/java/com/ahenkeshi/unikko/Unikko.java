package com.ahenkeshi.unikko;

import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.ahenkeshi.unikko.utils.HardConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;

public class Unikko implements ClientModInitializer, ModInitializer {
	public static final boolean DEV_MODE = true;

	public static final String MODID = "Unikko";
	public static String REL_DATE = "2021/08/21";
	public static String VERSION = "1.0.1";

	@Override
	public void onInitialize()	{
		try {HardConfigUtils.createFile();} catch (IOException e) {e.printStackTrace();}
		SoftConfigUtils.saveBooleansToConfigFile();

		if(DEV_MODE){VERSION += "-dev";} else{REL_DATE = "";}

		System.out.println("Unikko: what's the difference between onInitialize and onInitializeClient");
		CommandHandler.init();
	}

	@Override
	public void onInitializeClient() {
		System.out.println("Unikko: i may never know");
	}

}
