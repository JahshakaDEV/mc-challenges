/*
 * Copyright (C) 2022 Jannik Oeschger. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package de.jahshaka.mcchallenges.utils.timer;

import de.jahshaka.mcchallenges.McChallenges;
import org.bukkit.plugin.java.JavaPlugin;


public class TimerManager {


	McChallenges mcChallenges = JavaPlugin.getPlugin(McChallenges.class);

	private Timer timer;

	/**
	 * Instantiates a new Timer manager.
	 */
	public TimerManager() {
		timer = new Timer();
	}

	/**
	 * Starts the timer.
	 */
	public void startTimer() {
		timer.startTimer();
	}

	/**
	 * Pauses the timer.
	 */
	public void pauseTimer() {
		timer.stopTimer();
	}

	/**
	 * Resets the timer.
	 */
	public void resetTimer() {
		timer.resetTimer();
	}

	/**
	 * Resumes the timer.
	 */
	public void resumeTimer() {
		timer.startTimer();
	}
}