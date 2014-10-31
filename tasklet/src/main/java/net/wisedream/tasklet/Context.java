package net.wisedream.tasklet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Contextual data provided to {@link Task}s.
 * <p/>
 * <i>Note: It's held by {@link ThreadLocal}, you should be careful when using
 * with thread pool.</i>
 * 
 * @author pseudo
 * @Created Oct 13, 2014
 */
public class Context {
	protected static final ThreadLocal<Context> CURRENT = new ThreadLocal<Context>();

	protected final ConcurrentMap<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	public static Context getCurrent() {
		return CURRENT.get();
	}

	public static void setCurrent(Context context) {
		CURRENT.set(context);
	}

	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	public void putAttributes(Map<String, ?> attributes) {
		if ((attributes != null) && (!attributes.isEmpty()))
			synchronized (this.attributes) {
				this.attributes.putAll(attributes);
			}
	}

	public <T> void putAttrib(String key, T value) {
		this.attributes.put(key, value);
	}

	public <T> Context withAttrib(String key, T value) {
		putAttrib(key, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttrib(String key) {
		return (T) this.attributes.get(key);
	}
}
