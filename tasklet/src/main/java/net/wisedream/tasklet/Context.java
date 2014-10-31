package net.wisedream.tasklet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contextual data and services provided to {@link Task}s.
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
	protected volatile ExecutorService executorService;
	protected Logger logger;

	public Context() {
		this.logger = LoggerFactory.getLogger("worklet");// default logger
	}

	public Context(String logger) {
		this.logger = LoggerFactory.getLogger(logger);
	}

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

	public Logger getLogger() {
		return this.logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * You should call {@link #setExecutorService(ExecutorService)} before use.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends ExecutorService> T getExecutorService() {
		if(this.executorService == null)
			throw new RuntimeException("executorService hasn't been set, you should set it before use");
		return (T) this.executorService;
	}

	public <T extends ExecutorService> void setExecutorService(T executorService) {
		this.executorService = executorService;
	}

}
