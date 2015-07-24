package net.wisedream.tasklet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pseudo on 4/20/15.
 */
public class TaskContext{
    private static ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new ConcurrentHashMap<String, Object>();
        }
    };

    public <T> T getContextualObject(String key) {
        return (T) CONTEXT.get().get(key);
    }

    public void putContextualObject(String key, Object info) {
        CONTEXT.get().put(key, info);
    }

}
