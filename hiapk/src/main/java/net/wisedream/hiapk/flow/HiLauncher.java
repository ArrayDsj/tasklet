package net.wisedream.hiapk.flow;

import net.wisedream.hiapk.util.MHttpClient;
import net.wisedream.tasklet.Context;
import net.wisedream.tasklet.Launcher;
import net.wisedream.tasklet.Manager;
import net.wisedream.tasklet.Task;
import net.wisedream.tasklet.manager.StackManager;

public class HiLauncher extends Launcher {

	@Override
	public Task configureTask() {
		Manager manager = new StackManager() {
			@Override
			public void onStart() {
				// 初始化用到的资源
				MHttpClient.init();
//				MHttpClient.setProxy("127.0.0.1:8080");
				this.addTask(new Step01());
			}

			@Override
			public void onFinish() {
				// 释放资源
				MHttpClient.release();
			}

			@Override
			public void onCatch(Task task, Exception e) {
				e.printStackTrace();
			}
		};
		return manager;
	}

	@Override
	public Context configureContext() {
		return new Context();
	}
}
