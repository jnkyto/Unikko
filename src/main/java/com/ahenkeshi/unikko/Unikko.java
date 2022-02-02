/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko;

import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.modules.DiscordRPC;
import com.ahenkeshi.unikko.utils.config.HardConfigUtils;
import com.ahenkeshi.unikko.utils.config.SoftConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Unikko implements ModInitializer {
	public static final boolean DEV_MODE = true;

	public static final ModContainer MOD = FabricLoader.getInstance()
			.getModContainer("unikko")
			.orElseThrow(NullPointerException::new);

	public static final String MODID = MOD.getMetadata().getName();
	public static final String VERSION = MOD.getMetadata().getVersion().getFriendlyString();

	public static final Logger LOGGER = LogManager.getLogger();
	public static SoftConfig SOFTCONFIG;

	@Override
	public void onInitialize()	{
		LOGGER.info("Started initialization...");
		long initStart = System.currentTimeMillis();

		try {HardConfigUtils.createFile();} catch (IOException e) {e.printStackTrace();}
		CommandHandler.init();

		long initDuration = System.currentTimeMillis() - initStart;
		SOFTCONFIG = new SoftConfig(HardConfigUtils.getConfigMap());
		LOGGER.info("Initialization took " + initDuration + "ms.");
	}
}
