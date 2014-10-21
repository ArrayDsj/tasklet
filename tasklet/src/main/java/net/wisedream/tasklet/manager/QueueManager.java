package net.wisedream.tasklet.manager;

import java.util.LinkedList;
import java.util.Queue;

import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * Tasks are executed within a queue.
 * 
 * @author pseudo
 * @Created Oct 13, 2014
 */
public class QueueManager extends Manager {
	protected Queue<Task> taskQueue = new LinkedList<Task>();

	@Override
	protected void mainLogic(Manager manager) {
		Task currentTask = null;
		while ((currentTask = this.taskQueue.poll()) != null) {
			try {
				d("Task: <<<" + currentTask.getTag() + ">>>");
				currentTask.perform(this);
			} catch (Exception e) {
				onCatch(currentTask, e);
			}
		}
	}

	/**
	 * Adds a {@link Task} to the queue.
	 * 
	 * @param task
	 */
	public void addTask(Task task) {
		this.taskQueue.add(task);
	}
}
