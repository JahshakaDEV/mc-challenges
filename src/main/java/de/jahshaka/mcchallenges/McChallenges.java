/*
 * Copyright (C) 2022 Jannik Oeschger. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package de.jahshaka.mcchallenges;

import de.jahshaka.mcchallenges.commands.CommandsHelper;
import de.jahshaka.mcchallenges.commands.TimerCommand;
import de.jahshaka.mcchallenges.utils.config.ConfigManager;
import de.jahshaka.mcchallenges.utils.timer.TimerManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public final class McChallenges extends JavaPlugin {


	public ConfigManager configManager;
	CommandsHelper commandsHelper;
	Logger mainLogger = Logger.getLogger("Challenges");

	private BukkitAudiences audience;

	/**
	 * Audiences bukkit audiences.
	 *
	 * @return the bukkit audiences
	 */
	public BukkitAudiences audiences() {
		if (this.audience == null) {
			mainLogger.severe("Tried to access audience when the plugin was disabled!");
		}
		return this.audience;
	}

	@Override
	public void onEnable() {
		// Plugin startup logic
		init();
	}


	private void init() {
		configManager = new ConfigManager();
		commandsHelper = new CommandsHelper();
		registerCommands();
		this.audience = BukkitAudiences.create(this);
	}

	private void registerCommands() {
		this.getCommand("timer").setExecutor(new TimerCommand());
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		TimerManager timerManager = new TimerManager();
		timerManager.pauseTimer();
		if (this.audience != null) {
			this.audience.close();
			this.audience = null;
		}
	}
}