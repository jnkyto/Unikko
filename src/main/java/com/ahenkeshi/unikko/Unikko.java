/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko;

import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.config.SoftConfig;
import com.ahenkeshi.unikko.utils.config.HardConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Unikko implements ClientModInitializer, ModInitializer {
	public static final boolean DEV_MODE = true;

	public static final String MODID = "Unikko";
	public static final String VERSION = "1.1.0";
	public static String REL_DATE = "2021/11/18";
	public static String DEV = "-dev";

	public static final ModContainer MOD = FabricLoader.getInstance()
			.getModContainer("unikko")
			.orElseThrow(NullPointerException::new);

	public static final Logger logger = LogManager.getLogger();
	public static SoftConfig softConfig;

	@Override
	public void onInitialize()	{
		logger.info("Started initialization...");
		long initStart = System.currentTimeMillis();

		try {HardConfigUtils.createFile();} catch (IOException e) {e.printStackTrace();}

		if(!DEV_MODE){
			DEV = "";
			REL_DATE = "";
		}

		CommandHandler.init();
		long initDuration = System.currentTimeMillis() - initStart;
		softConfig = new SoftConfig(HardConfigUtils.getConfigMap());
		logger.info("Initialization took " + initDuration + "ms.");
	}

	@Override
	public void onInitializeClient() {}

}
