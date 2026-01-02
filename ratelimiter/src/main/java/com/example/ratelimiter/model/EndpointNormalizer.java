package com.example.ratelimiter.model;

public class EndpointNormalizer {

    public static String normalize(String path) {

        if (path == null) return "UNKNOWN";

        // replace numbers
        path = path.replaceAll("/\\d+", "/{id}");

        // remove trailing slash
        if (path.endsWith("/") && path.length() > 1) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }
}

