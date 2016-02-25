package net.wisedream.tasklet;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by zxp on 3/23/15.
 */
public class TaskPerformer implements Task {
    private List<ExceptionHandler> exceptionHandlers = new LinkedList<ExceptionHandler>();
    private Task entryTask;

    public TaskPerformer(Task entryTask) {
        this.entryTask = entryTask;
    }

    @Override
    public Terminator perform(TaskContext context) throws Exception {
        Terminator terminator = Terminator.SUCCESS;
        onStart(context);
        Task nextTask = entryTask;
        try {
            while (nextTask != null)
                if (nextTask instanceof Terminator) {
                    terminator = (Terminator) nextTask;
                    break;
                } else {
                    nextTask = nextTask.perform(context);
                }
        } catch (Exception e) {
            for (ExceptionHandler handler : exceptionHandlers)
                handler.handle(e);
            terminator = Terminator.CRASH;
        }
        onExit(context);
        return terminator;
    }

    protected void onStart(TaskContext context) {

    }

    protected void onExit(TaskContext context) {

    }

    public TaskPerformer withExceptionHandler(ExceptionHandler handler) {
        this.exceptionHandlers.add(handler);
        return this;
    }

    public static interface ExceptionHandler {
        public void handle(Exception e);
    }
}
