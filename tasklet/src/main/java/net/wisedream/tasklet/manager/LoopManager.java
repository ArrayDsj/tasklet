package net.wisedream.tasklet.manager;

import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * Perform a single task in a loop.
 * 
 * @author pseudo
 * @Created Oct 20, 2014
 */
public class LoopManager extends LogicalManager {
	protected Task task;

	protected void mainLogic(Manager manager) {
		while (task != null) {
			try {
				task.perform(this);
			} catch (Exception e) {
				onCatch(task, e);
			}
		}

	}

	protected void onCatch(Task currentTask, Exception e) {
	}

	public void addTask(Task task) {
		this.task = task;
	}

	public void addEmergencyTask(Task task) {
		this.task = task;
	}

	public void deleteTask(Task task) {
		this.task = null;
	}

	public void deleteAllTasks() {
		this.task = null;
	}

	public void onStart() {

	}

	public void onFinish() {

	}

}
