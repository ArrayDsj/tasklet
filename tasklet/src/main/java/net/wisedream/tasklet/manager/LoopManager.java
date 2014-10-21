package net.wisedream.tasklet.manager;

import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * Perform a single task in a loop.
 * 
 * @author pseudo
 * @Created Oct 20, 2014
 */
public class LoopManager extends Manager {
	private Task currentTask;

	@Override
	public void addTask(Task task) {
		this.currentTask = task;

	}

	@Override
	protected void mainLogic(Manager manager) {
		while (currentTask != null) {
			try {
				d("Task: <<<" + currentTask.getTag() + ">>>");
				currentTask.perform(this);
			} catch (Exception e) {
				onCatch(currentTask, e);
			}
		}

	}

}
