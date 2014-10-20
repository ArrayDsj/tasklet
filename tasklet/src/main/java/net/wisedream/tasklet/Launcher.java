package net.wisedream.tasklet;

/**
 * The entry of a complete task.
 * 
 * @author pseudo
 * @Created Oct 13, 2014
 */
public abstract class Launcher {
	
	public void launch() {
		Context.setCurrent(this.configureContext());
		Task task = configureTask();
		try {
			task.perform(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Configure the execution environment.
	 * @return
	 */
	public abstract Context configureContext();
	
	/**
	 * Configure the task to be performed.<br/>
	 * <B>You should return a newly created task</B>
	 * @return A newly created Task
	 */
	public abstract Task configureTask();

}
