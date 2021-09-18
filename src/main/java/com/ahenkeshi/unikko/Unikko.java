package com.ahenkeshi.unikko;

import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.ahenkeshi.unikko.utils.HardConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Unikko implements ClientModInitializer, ModInitializer {
	public static final boolean DEV_MODE = true;

	public static final String MODID = "Unikko";
	public static final String VERSION = "1.1.0";
	public static String REL_DATE = "2021/09/18";
	public static String DEV = "-dev";

	public static final Logger logger = LogManager.getLogger();

	@Override
	public void onInitialize()	{
		logger.info("Started initialization...");
		long initStart = System.currentTimeMillis();

		try {HardConfigUtils.createFile();} catch (IOException e) {e.printStackTrace();}
		SoftConfigUtils.saveBooleansToConfigFile();

		if(!DEV_MODE){
			DEV = "";
			REL_DATE = "";
		}

		CommandHandler.init();
		long initDuration = System.currentTimeMillis() - initStart;
		logger.info("Initialization took " + initDuration + "ms.");
	}

	@Override
	public void onInitializeClient() {}

}
