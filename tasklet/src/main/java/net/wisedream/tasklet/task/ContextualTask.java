package net.wisedream.tasklet.task;

import net.wisedream.tasklet.Context;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;

public abstract class ContextualTask implements Task {
	protected Context context;

	public ContextualTask() {
		context = Context.getCurrent();
		if (context == null)
			throw new RuntimeException("Context is null");
	}

	public abstract void perform(Manager manager);

	public Context getContext() {
		return this.context;
	}

	public <T> T getContextualAttrib(String key) {
		T attrib = context.getAttrib(key);
		if (attrib == null)
			throw new RuntimeException(String.format(
					"Contextual attribute: '%s' is null", key));
		return attrib;
	}

	public <T> void putContextualAttrib(String key, T value) {
		context.putAttrib(key, value);
	}
}
