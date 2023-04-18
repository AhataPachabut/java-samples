package com.epam.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readFromFile(String filePath) throws FileNotFoundException {
        //todo
        return objectMapper.readValue(new FileInputStream(filePath), T);
    }

    public static void writeToFile(String filePath, Object obj) throws IOException {
        objectMapper.writeValue(new FileOutputStream(filePath), obj);
    }
}
