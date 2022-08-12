/*
 * Copyright (C) 2022 Jannik Oeschger. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package de.jahshaka.mcchallenges.utils.config;

import de.jahshaka.mcchallenges.McChallenges;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


/**
 * The Config manager for creating and saving configs for example.
 */
public class ConfigManager {

	McChallenges mcChallenges = JavaPlugin.getPlugin(McChallenges.class);
	Logger logger = Logger.getLogger("Config-Manager");

	/**
	 * The Timers.yml config file.
	 */
	File timersConfigFile = new File(mcChallenges.getDataFolder(), "timers.yml");
	/**
	 * The Constants.yml config file.
	 */
	File constantsConfigFile = new File(mcChallenges.getDataFolder(), "constants.yml");
	/**
	 * The Permissions.yml config file.
	 */
	File permissionsConfigFile = new File(mcChallenges.getDataFolder(), "permissions.yml");

	private FileConfiguration timersConfig;
	private FileConfiguration constantsConfig;
	private FileConfiguration permissionsConfig;

	/**
	 * Instantiates a new Config manager.
	 */
	public ConfigManager() {
		createCustomConfigs();
	}

	/**
	 * Gets a String from the constants.yml config.
	 *
	 * @param path the path to the constant
	 * @return the constant
	 */
	public String getConstant(String path) {
		return this.getConstantsConfig().getString(path);
	}

	/**
	 * Gets timers config.
	 *
	 * @return the timers config
	 */
	public FileConfiguration getTimersConfig() {
		return this.timersConfig;
	}

	/**
	 * Gets constants config.
	 *
	 * @return the constants config
	 */
	public FileConfiguration getConstantsConfig() {
		return this.constantsConfig;
	}

	/**
	 * Gets permissions config.
	 *
	 * @return the permissions config
	 */
	public FileConfiguration getPermissionsConfig() {
		return this.permissionsConfig;
	}

	private void createCustomConfigs() {
		if (!timersConfigFile.exists()) {
			timersConfigFile.getParentFile().mkdirs();
			mcChallenges.saveResource("timers.yml", false);
		}

		if (!constantsConfigFile.exists()) {
			constantsConfigFile.getParentFile().mkdirs();
			mcChallenges.saveResource("constants.yml", false);
		}

		if (!permissionsConfigFile.exists()) {
			permissionsConfigFile.getParentFile().mkdirs();
			mcChallenges.saveResource("permissions.yml", false);
		}

		timersConfig = new YamlConfiguration();
		constantsConfig = new YamlConfiguration();
		permissionsConfig = new YamlConfiguration();
		try {
			timersConfig.load(timersConfigFile);
			constantsConfig.load(constantsConfigFile);
			permissionsConfig.load(permissionsConfigFile);
		} catch (IOException | InvalidConfigurationException e) {
			logger.severe("1660240331873: Couldn't load configs: " + e.getMessage());
		}
	}

	/**
	 * Saves the timers config.
	 */
	public void saveTimersConfig() {
		try {
			timersConfig.save(timersConfigFile);
		} catch (IOException e) {
			logger.warning("1660240341663: Couldn't save Timer Config: " + e.getMessage());
		}
	}
}