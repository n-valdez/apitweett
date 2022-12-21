package com.example.twitterapi.api.helper;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StatusUtils {

    @Value("${apiVersion}")
    private static final String apiVersion = null;

    private StatusUtils() {
        throw new IllegalStateException("Utility class");
    }

}
