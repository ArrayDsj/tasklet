package net.wisedream.tasklet;


public interface Manager extends Task {

	void addTask(Task task);
	
	void addEmergencyTask(Task task);

	void deleteTask(Task task);

	void deleteAllTasks();

	void onStart();

	void onFinish();

}
