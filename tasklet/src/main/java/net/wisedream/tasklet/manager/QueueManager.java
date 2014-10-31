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
public class QueueManager extends LogicalManager {
	protected Queue<Task> taskQueue = new LinkedList<Task>();

	@Override
	protected void mainLogic(Manager manager) {
		Task currentTask = null;
		while ((currentTask = this.taskQueue.poll()) != null) {
			try {
				currentTask.perform(this);
			} catch (Exception e) {
				onCatch(currentTask, e);
			}
		}
	}

	protected void onCatch(Task currentTask, Exception e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Adds a {@link Task} to the queue.
	 * 
	 * @param task
	 */
	public void addTask(Task task) {
		this.taskQueue.add(task);
	}

	public void deleteTask(Task task) {
		this.taskQueue.remove(task);
	}

	public void deleteAllTasks() {
		this.taskQueue.clear();
	}

	public void onStart() {

	}

	public void onFinish() {

	}
}
