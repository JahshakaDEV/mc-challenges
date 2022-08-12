/*
 * Copyright (C) 2022 Jannik Oeschger. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package de.jahshaka.mcchallenges.commands;

import de.jahshaka.mcchallenges.McChallenges;
import de.jahshaka.mcchallenges.utils.timer.TimerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * The timer command is used for starting, pausing and resetting the timer
 */
public class TimerCommand implements CommandExecutor {

	McChallenges mcChallenges = JavaPlugin.getPlugin(McChallenges.class);
	CommandsHelper cH = new CommandsHelper();
	Logger logger = Logger.getLogger("Timer-CMD");
	TimerManager timerManager = null;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof Player player) {
			if (command.getName().equalsIgnoreCase(command.getName())) {
				if (player.hasPermission(cH.getPermissions("timer"))) {
					if (timerManager == null) {
						timerManager = new TimerManager();
					}
					if (args.length > 2 || args.length == 0) {
						cH.sendMessage(player, "&c" + command.getUsage());
						return true;
					}
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("reset")) {
							if (player.hasPermission(cH.getPermissions("timer", "reset"))) {
								cH.sendMessage(player, "&cIf you really want to reset the timer type /reset confirm!");
							} else {
								cH.sendNoPermsMessage(player);
							}
							return true;
						} else if (args[0].equalsIgnoreCase("pause")) {
							if (player.hasPermission(cH.getPermissions("timer", "pause"))) {
								timerManager.pauseTimer();
							} else {
								cH.sendNoPermsMessage(player);
							}
							return true;
						} else if (args[0].equalsIgnoreCase("resume") || args[0].equalsIgnoreCase("start")) {
							if (player.hasPermission(cH.getPermissions("timer", "resume"))) {
								timerManager.resumeTimer();
							} else {
								cH.sendNoPermsMessage(player);
							}
							return true;
						} else {
							cH.sendMessage(player, "&c" + command.getUsage());
							return true;
						}
					} else {
						if (args[0].equalsIgnoreCase("reset") && args[1].equalsIgnoreCase("confirm")) {
							timerManager.resetTimer();
							cH.sendMessage(player, "The timer was reset!");
							return true;
						} else {
							cH.sendMessage(player, "&c" + command.getUsage());
						}
						return true;
					}
				} else {
					cH.sendNoPermsMessage(player);
					return true;
				}
			}
		} else {
			logger.info("Timer command can only be executed by players.");
			return true;
		}
		return false;
	}
}