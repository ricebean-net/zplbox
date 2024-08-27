package com.meixxi.zplbox;

import com.meixxi.zplbox.service.about.AboutService;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private AboutService aboutService;

	/**
	 * Application main entrance point.
	 *
	 * @param args Application parameters.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Event is called after applications start up.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onStartUp() {

		// some informal log messages
		log.warn(String.format("%s %s has started. (rev: %s)", aboutService.getAppName(), aboutService.getVersion(), aboutService.getCommitId()));
	}

	/**
	 * Is called before application is shutdown.
	 */
	@PreDestroy
	public void destroy() {
		log.warn("Application is going to be shutdown...");
	}

}
