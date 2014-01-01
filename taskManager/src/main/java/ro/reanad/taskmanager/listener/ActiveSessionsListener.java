package ro.reanad.taskmanager.listener;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class ActiveSessionsListener implements HttpSessionListener {
	private static final Logger logger = Logger.getLogger(ActiveSessionsListener.class);
	private final AtomicInteger sessionCount = new AtomicInteger(0);

	public void sessionCreated(HttpSessionEvent event) {
		sessionCount.incrementAndGet();
		logger.info("Session Created: " + event.getSession().getId());
		logger.info("Total active sessions: " + sessionCount);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		sessionCount.decrementAndGet();
		logger.info("Session Destroyed: " + event.getSession().getId());
		logger.info("Total Sessions: " + sessionCount);
	}
}
