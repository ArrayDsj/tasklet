package net.wisedream.tasklet.manager;

import java.util.LinkedList;

import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

/**
 * Tasks are executed within a stack.
 * 
 * @author pseudo
 * @Created Oct 13, 2014
 */
public class StackManager extends Manager {
	protected Stack<Task> taskStack = new Stack<Task>();

	/**
	 * Main logic for this manager
	 * 
	 * @return
	 */
	protected void mainLogic(Manager manager) {
		// main logic
		Task currentTask;
		while ((currentTask = taskStack.pop()) != null) {
			try {
				d("Task: <<<" + currentTask.getTag() + ">>>");
				currentTask.perform(this);
			} catch (Exception e) {
				onCatch(currentTask, e);
			}
		}
	}

	/**
	 * Adds a {@link Task} to the stack.
	 */
	@Override
	public void addTask(Task task) {
		this.taskStack.push(task);
	}

	public static class Stack<T> {
		private LinkedList<T> stack = new LinkedList<T>();

		public void push(T obj) {
			this.stack.push(obj);
		}

		public T pop() {
			return this.stack.poll();
		}

		/**
		 * Retrieves, but does not remove, the head (first element) of this
		 * stack.
		 * 
		 * @return the head of this stack, or <tt>null</tt> if this stack is
		 *         empty
		 */
		public T peek() {
			return this.stack.peek();
		}

		public boolean isEmpty() {
			return this.stack.isEmpty();
		}

		@Override
		public String toString() {
			return this.stack.toString();
		}

	}
}
