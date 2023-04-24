package com.epam.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readFromFile(String filePath, TypeReference<T> typeRef) throws IOException {
        return objectMapper.readValue(new FileInputStream(filePath), typeRef);
    }

    public static void writeToFile(String filePath, Object obj) throws IOException {
        objectMapper.writeValue(new FileOutputStream(filePath), obj);
    }
}
