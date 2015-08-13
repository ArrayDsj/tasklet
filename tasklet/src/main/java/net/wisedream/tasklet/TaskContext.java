package net.wisedream.tasklet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pseudo on 4/20/15.
 */
public class TaskContext {
    private Map<String, Object> container = new ConcurrentHashMap<String, Object>();

    public <T> T getContextualObject(String key) {
        return (T) container.get(key);
    }

    public void putContextualObject(String key, Object info) {
        container.put(key, info);
    }

}
