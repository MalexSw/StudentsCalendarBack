package com;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.model.Subject;
import com.util.URLDecode;

@SpringBootApplication
public class DemoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
    private static final String CALENDAR_URL = "https://apps.usos.agh.edu.pl/services/tt/upcoming_ical?lang=pl&user_id=138230&key=SjJz4MZnfTsGCjUPjxye";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner logCalendarContents() {
        return args -> {
            List<Subject> subjects = URLDecode.decodeURL(CALENDAR_URL);
            if (!subjects.isEmpty()) {
                log.info("Decoded {} calendar entries", subjects.size());
            }
        };
    }
}
