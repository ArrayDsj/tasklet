package net.wisedream.tasklet.manager;

import net.wisedream.tasklet.Manager;

public abstract class LogicalManager implements Manager {

	public void perform(Manager manager) {
		onStart();
		try {
			mainLogic(manager);
		} finally {
			onFinish();
		}
	}

	protected abstract void mainLogic(Manager manager);

}
