package com.neo.vibecheck.logic.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JSONMapper {

    // Creates a private constructor to prevent one from being generated at Runtime
    private JSONMapper() {
        throw new IllegalStateException("Utility class");
    }

    private static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()); // Adds module for LocalDate support

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
