/*
 * Copyright (C) 2022 Jannik Oeschger. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package de.jahshaka.mcchallenges.utils.timer;

import de.jahshaka.mcchallenges.McChallenges;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Logger;


public class Timer {


	private final String timePassedPath = "timer.timePassed";
	/**
	 * The Time passed in seconds.
	 */
	protected long timePassed = 0;
	/**
	 * The Timer task id to stop the scheduler.
	 */
	protected int timerTaskID = -1;
	/**
	 * The Auto save task id to stop the scheduler.
	 */
	protected int autoSaveTaskID = -1;

	McChallenges mcChallenges = JavaPlugin.getPlugin(McChallenges.class);
	Logger logger = Logger.getLogger("Timer-Logger");

	/**
	 * Instantiates a new Timer.
	 */
	public Timer() {
		if (getTimePassed() == 0) {
			setTimePassed(getTimePassedFromConfig());
		}
	}

	/**
	 * Gets the timer task id from the timer config.
	 *
	 * @return the timer task id
	 */
	public int getTimerTaskID() {
		return mcChallenges.configManager.getTimersConfig().getInt("timer.taskID");
	}

	/**
	 * Sets the timer task id variable and config.
	 *
	 * @param timerTaskID the timer task id
	 */
	public void setTimerTaskID(int timerTaskID) {
		this.timerTaskID = timerTaskID;
		mcChallenges.configManager.getTimersConfig().set("timer.taskID", timerTaskID);
		mcChallenges.configManager.saveTimersConfig();
	}

	/**
	 * Gets auto save task id from config.
	 *
	 * @return the auto save task id
	 */
	public int getAutoSaveTaskID() {
		return mcChallenges.configManager.getTimersConfig().getInt("timer.autoSaveTaskID");
	}

	/**
	 * Sets auto save task id variable and in config.
	 *
	 * @param autoSaveTaskID the auto save task id
	 */
	public void setAutoSaveTaskID(int autoSaveTaskID) {
		this.autoSaveTaskID = autoSaveTaskID;
		mcChallenges.configManager.getTimersConfig().set("timer.autoSaveTaskID", autoSaveTaskID);
		mcChallenges.configManager.saveTimersConfig();
	}

	/**
	 * Gets time passed path.
	 *
	 * @return the time passed path
	 */
	public String getTimePassedPath() {
		return timePassedPath;
	}

	/**
	 * Gets time passed in seconds.
	 *
	 * @return the time passed in seconds
	 */
	public long getTimePassed() {
		return timePassed;
	}

	/**
	 * Sets time passed in seconds.
	 *
	 * @param timePassed the time passed in seconds
	 */
	public void setTimePassed(long timePassed) {
		this.timePassed = timePassed;
	}

	private long getTimePassedFromConfig() {
		return mcChallenges.configManager.getTimersConfig().getLong(timePassedPath);
	}

	/**
	 * Starts the timer.
	 */
	public void startTimer() {
		if (getTimerTaskID() == -1) {
			BukkitTask timerTask = Bukkit.getScheduler().runTaskTimerAsynchronously(mcChallenges, () -> {
				setTimePassed(getTimePassed() + 1);
				sendTimer();
			}, 20L, 20L);
			setTimerTaskID(timerTask.getTaskId());
			logger.info("Started Timer main-scheduler with taskID: " + timerTask.getTaskId());
			startAutoSaving();
		}
	}


	/**
	 * Resets the timer.
	 */
	public void resetTimer() {
		stopTimer();
		setTimePassed(0);
		mcChallenges.configManager.getTimersConfig().set(timePassedPath, 0);
		mcChallenges.configManager.saveTimersConfig();
	}

	private void startAutoSaving() {
		BukkitTask autoSaveTask = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(mcChallenges, this::saveTimer, 20L * 60L, 20L * 60L);
		setAutoSaveTaskID(autoSaveTask.getTaskId());
		logger.info("Started Timer autosave-scheduler with taskID: " + autoSaveTask.getTaskId());
	}

	/**
	 * Stops the timer and cancels the scheduler.
	 */
	public void stopTimer() {
		Bukkit.getServer().getScheduler().cancelTask(getTimerTaskID());
		Bukkit.getServer().getScheduler().cancelTask(getAutoSaveTaskID());
		setTimerTaskID(-1);
		setAutoSaveTaskID(-1);
		saveTimer();
	}

	private void saveTimer() {
		logger.info("Saving timer at " + getTimePassed() + "seconds in...");
		mcChallenges.configManager.getTimersConfig().set(timePassedPath, getTimePassed());
		mcChallenges.configManager.saveTimersConfig();
	}

	private void sendTimer() {
		int minutes = 0;
		int hours = 0;
		int days = 0;
		long passedTime = getTimePassed();
		while (passedTime >= 60) {
			minutes++;
			passedTime = (passedTime - 60);
		}
		while (minutes >= 60) {
			hours++;
			minutes = (minutes - 60);
		}
		while (hours >= 24) {
			days++;
			hours = (hours - 24);
		}
		String message = "&6&l";
		if (days > 0) {
			message = message + days + "d ";
			if (hours != 0) {
				if (hours > 9) {
					message = message + hours + ":";
				} else {
					message = message + "0" + hours + ":";
				}
			} else {
				message = message + "00:";
			}
			if (minutes != 0) {
				if (minutes > 9) {
					message = message + minutes + ":";
				} else {
					message = message + "0" + minutes + ":";
				}
			} else {
				message = message + "00:";
			}
			if (passedTime > 9) {
				message = message + passedTime;
			} else {
				message = message + "0" + passedTime;
			}
		} else {
			if (hours != 0) {
				if (hours > 9) {
					message = message + hours + ":";
				} else {
					message = message + "0" + hours + ":";
				}
			}
			if (minutes != 0) {
				if (minutes > 9) {
					message = message + minutes + ":";
				} else {
					message = message + "0" + minutes + ":";
				}
				if (passedTime > 9) {
					message = message + passedTime;
				} else {
					message = message + "0" + passedTime;
				}
			} else {
				message = message + "00:";
				if (passedTime > 9) {
					message = message + passedTime;
				} else {
					message = message + "0" + passedTime;
				}
			}
		}


		mcChallenges.audiences().players().sendActionBar(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
	}
}