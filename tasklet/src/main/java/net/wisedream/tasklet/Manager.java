package net.wisedream.tasklet;

/**
 * The entry of task/sub-task.
 * 
 * @author pseudo
 * @Created Oct 13, 2014
 */
public abstract class Manager extends Task {
	/**
	 * Cause the tasks to be performed.
	 */
	public void perform(Manager manager) {
		onStart();
		try {
			mainLogic(manager);
		} finally {
			onFinish();
		}
	}

	public abstract void addTask(Task task);

	/**
	 * Main logic for this manager. You should handle all exceptions within this
	 * method.
	 * 
	 * @return
	 */
	protected abstract void mainLogic(Manager manager);

	/**
	 * Perform some initialization work.
	 */
	public void onStart() {
	}

	/**
	 * Called when an unhandled exception occurred during the execution of
	 * {@link Task#perform()}. This will print the exception and continue the
	 * execution of Manager by default.
	 * 
	 * @param e
	 */
	public void onCatch(Task task, Exception e) {
		e("Unhandled Exception: " + e.getClass().getSimpleName() + ", "
				+ e.getMessage());
	}

	/**
	 * Do some clean stuff.
	 */
	public void onFinish() {
	}
}
