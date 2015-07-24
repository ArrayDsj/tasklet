package net.wisedream.tasklet;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    static final String TAG = "contextual key";

    @org.junit.Test
    public void test1() {
        TaskContext context = new TaskContext();
        Task incrementer = new Task() {
            private int i = 0;

            @Override
            public Task perform(TaskContext context) throws Exception {
                if (++i >= 5) {
                    context.putContextualObject(TAG, i);
                    return null;
                } else
                    return this;
            }
        };
        TaskPerformer performer = new TaskPerformer(incrementer,context);
        performer.perform();
        Assert.assertEquals(5,context.getContextualObject(TAG));
    }

    @Test
    public void test2() {
        TaskContext context = new TaskContext();
        context.putContextualObject(TAG,0);
        TaskGroup group = new TaskGroup() {
            @Override
            public Task nextTask() {
                return new Task() {
                    @Override
                    public Task perform(TaskContext context) throws Exception {
                        System.out.println("incrementer result: "+context.getContextualObject(TAG));
                        return null;
                    }
                };
            }
        };
        for(int i=0; i<5; i++){
            group.addTask(new Task() {
                @Override
                public Task perform(TaskContext context) throws Exception {
                    int current = context.getContextualObject(TAG);
                    context.putContextualObject(TAG, ++current);
                    return null;
                }
            });
        }
        TaskPerformer performer=new TaskPerformer(group, context);
        performer.perform();
        Assert.assertEquals(5,context.getContextualObject(TAG));
    }
}
