package net.wisedream.hiapk.flow;

import java.util.concurrent.TimeUnit;

import net.wisedream.tasklet.Context;
import net.wisedream.tasklet.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HiapkTask implements Task {
	protected Logger logger = LoggerFactory.getLogger("hiapk");
	protected Context context;
	protected String tag;

	public HiapkTask() {
		// this.context = Context.getCurrent();
		this.tag = getClass().getSimpleName();
		if (this.tag.isEmpty())
			this.tag = getClass().getName();
		context = Context.getCurrent();
		if (context == null)
			throw new RuntimeException("Context is null");
	}

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
		logger.debug("[" + this.tag + "]\t" + msg);
	}

	public void i(String msg) {
		logger.info("[" + this.tag + "]\t" + msg);
	}

	public void w(String msg) {
		logger.warn("[" + this.tag + "]\t" + msg);
	}

	public void e(String msg) {
		logger.error("[" + this.tag + "]\t" + msg);
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
