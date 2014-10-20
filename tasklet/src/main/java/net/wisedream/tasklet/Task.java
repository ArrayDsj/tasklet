package net.wisedream.tasklet;

import java.util.concurrent.TimeUnit;

/**
 * Execution unit.
 *
 * @author pseudo
 * @Created Oct 13, 2014
 */
public abstract class Task {
	protected Context context ;

	protected String tag;

	public Task() {
		// this.context = Context.getCurrent();
		this.tag = getClass().getSimpleName();
		if (this.tag.isEmpty())
			this.tag = getClass().getName();
		context = Context.getCurrent();
		if(context==null)
			throw new RuntimeException("Context is null");
	}

	/**
	 * Task's Execution method
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract void perform(Manager manager);

	public void pause(int seconds) {
		pause(seconds, TimeUnit.SECONDS);
	}

	public void pause(int time, TimeUnit timeUnit) {
		i("sleep(s): " + time + " " + timeUnit.toString());
		try {
			Thread.sleep(timeUnit.toMillis(time));
		} catch (Exception e) {
		}
	}

	public void d(String msg) {
		context.getLogger().debug("[" + this.tag + "]\t" + msg);
	}

	public void i(String msg) {
		context.getLogger().info("[" + this.tag + "]\t" + msg);
	}

	public void w(String msg) {
		context.getLogger().warn("[" + this.tag + "]\t" + msg);
	}

	public void e(String msg) {
		context.getLogger().error("[" + this.tag + "]\t" + msg);
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Context getContext() {
		return this.context;
	}
}