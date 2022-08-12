/*
 * Copyright (C) 2022 Jannik Oeschger. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package de.jahshaka.mcchallenges.commands;

import de.jahshaka.mcchallenges.McChallenges;
import de.jahshaka.mcchallenges.utils.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * The Commands-Helper contains some functions to make command-classes more readable.
 */
public class CommandsHelper {


	McChallenges mcChallenges = JavaPlugin.getPlugin(McChallenges.class);
	ConfigManager configManager = new ConfigManager();

	/**
	 * Sends a message to a player, translating & to color codes.
	 *
	 * @param player  the player which receives the message
	 * @param message the message to be translated to color and send
	 */
	public void sendMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', mcChallenges.configManager.getConstant("constant.messages.prefix") + message));
	}

	/**
	 * Sends the no permission message from the constants.yml to the player
	 *
	 * @param player the player which receives the message
	 */
	public void sendNoPermsMessage(Player player) {
		sendMessage(player, mcChallenges.configManager.getConstant("constant.messages.noperms"));
	}

	/**
	 * Gets the permission string from the permissions.yml
	 *
	 * @param commandName the command name to get the permissions to
	 * @return the permission string for the command
	 */
	public String getPermissions(String commandName) {
		return configManager.getPermissionsConfig().getString("permissions.commands." + commandName);
	}

	/**
	 * Gets the permission string from the permissions.yml
	 *
	 * @param commandName the command name
	 * @param argument    the command argument to get the permission to
	 * @return the permission string for the command
	 */
	public String getPermissions(String commandName, String argument) {
		return configManager.getPermissionsConfig().getString("permissions.commands." + commandName + "." + argument);
	}

}