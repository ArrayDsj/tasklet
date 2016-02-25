package net.wisedream.tasklet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pseudo on 4/20/15.
 */
public abstract class TaskGroup implements Task{
    List<Task> tasks = new LinkedList<Task>();

    @Override
    public Task perform(TaskContext context) throws Exception {
        for(Task task : tasks){
            task.perform(context);
        }
        return nextTask();
    }

    public TaskGroup addTask(Task task){
        this.tasks.add(task);
        return this;
    }
    public abstract Task nextTask();
}
