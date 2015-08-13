package net.wisedream.tasklet;

import net.wisedream.tasklet.Task;
import net.wisedream.tasklet.TaskContext;

import java.util.Map;

/**
 * Created by pseudo on 15-8-12.
 */
public enum Terminator implements Task {
    SUCCESS,FAIL,CRASH;

    @Override
    public Task perform(TaskContext context) throws Exception {
        throw new Exception("Terminator mustn't perform");
    }
}
