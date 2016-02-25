import net.wisedream.tasklet.Task;
import net.wisedream.tasklet.TaskContext;
import net.wisedream.tasklet.TaskPerformer;
import net.wisedream.tasklet.Terminator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pseudo on 16-2-25.
 */
public class Test {
    @org.junit.Test
    public void simpleTest() throws Exception {
        Task payload3 = (cxt) -> {
            System.out.println("sending payload 3");
            return Terminator.SUCCESS;
        };
        Task payload2 = (cxt) -> {
            System.out.println("sending payload 2");
            return payload3;
        };
        Task payload1 = (cxt) -> {
            System.out.println("sending payload 1");
            return payload2;
        };
        TaskPerformer job = new TaskPerformer(payload1);
        switch (job.perform(null)) {
            case SUCCESS:
                System.out.println("job finished");
                break;
            default:
                System.out.println("something went wrong");
                break;
        }
    }

    @org.junit.Test
    public void testFibonacci() throws Exception {
        final int n = 10;
        final String KEY_LIST = "key";

        TaskContext cxt = new TaskContext();
        List<Integer> list = new ArrayList() {
            @Override
            public Object get(int index) {
                if (index >= 0)
                    return super.get(index);
                else
                    return 0;
            }
        };
        list.add(1);

        cxt.putContextualObject(KEY_LIST, list);
        Task entry = new Task() {
            @Override
            public Task perform(TaskContext context) throws Exception {
                List<Integer> tmp = context.getContextualObject(KEY_LIST);
                if (tmp.size() >= n)
                    return Terminator.SUCCESS;
                else {
                    tmp.add((tmp.get(tmp.size() - 1) + tmp.get(tmp.size() - 2)));
                    return this;
                }
            }
        };
        TaskPerformer performer = new TaskPerformer(entry).withExceptionHandler((e) -> e.printStackTrace());
        switch (performer.perform(cxt)) {
            case SUCCESS:
                System.out.println(cxt.getContextualObject(KEY_LIST).toString());
                break;
            default:
                System.out.println("something wrong");
        }
    }


}
