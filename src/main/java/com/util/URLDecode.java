package com.util;

import java.util.Collections;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.model.Subject;

/**
 * Utility responsible for fetching and eventually parsing calendar data from an
 * iCal endpoint.
 */
public final class URLDecode {

    private static final WebClient WEB_CLIENT = WebClient.create();

    private URLDecode() {
        // Utility class
    }

    public static List<Subject> decodeURL(String url) {
        String icalData = WEB_CLIENT.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("Fetched iCal data:\n" + icalData);
        return Collections.emptyList();
    }
}
