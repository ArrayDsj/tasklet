package net.wisedream.tasklet;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by zxp on 3/23/15.
 */
public class TaskPerformer {
    private List<ExceptionHandler> exceptionHandlers = new LinkedList<ExceptionHandler>();
    private Task entryTask;
    private TaskContext context;

    public TaskPerformer(Task entryTask, TaskContext context) {
        this.entryTask = entryTask;
        this.context = context;
    }

    public void perform() {
        onStart(context);
        Task nextTask = entryTask;
        try {
            while (nextTask != null)
                nextTask = nextTask.perform(context);
        } catch (Exception e) {
            for (ExceptionHandler handler : exceptionHandlers)
                handler.handle(e);
        }
        onExit(context);
    }

    protected void onStart(TaskContext context){

    }
    protected void onExit(TaskContext context){

    }

    public void addExceptionHandler(ExceptionHandler handler){
        this.exceptionHandlers.add(handler);
    }

    public static interface ExceptionHandler {
        public void handle(Exception e);
    }
}
