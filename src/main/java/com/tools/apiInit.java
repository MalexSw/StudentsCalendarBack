package com.tools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.DemoApplication;
import com.model.Subject;
import com.util.URLDecode;

public class apiInit {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
    private static ConfigurableApplicationContext context;
    private static final String CALENDAR_URL = "https://apps.usos.agh.edu.pl/services/tt/upcoming_ical?lang=pl&user_id=138230&key=SjJz4MZnfTsGCjUPjxye";

    public static void initAPI() {
        if (context == null) {
            context = SpringApplication.run(DemoApplication.class);
            log.info("API started successfully!");
        } else {
            log.info("API is already running!");
        }
    }

    // Call this to stop the API if needed
    public static void stopAPI() {
        if (context != null) {
            context.close();
            context = null;
            log.info("API stopped successfully!");
        }
    }

    @Bean
    CommandLineRunner logCalendarContents() {
        return args -> {
            List<Subject> subjects = URLDecode.decodeURL(CALENDAR_URL);
            if (!subjects.isEmpty()) {
                log.info("Decoded {} calendar entries", subjects.size());
            } else {
                log.info("No calendar entries decoded.");
            }
        };
    }
}
