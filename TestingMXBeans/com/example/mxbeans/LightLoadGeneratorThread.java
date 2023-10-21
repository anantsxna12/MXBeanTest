package com.example.mxbeans;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class LightLoadGeneratorThread extends LoadGeneratorThread {
    @Override
    public String getThreadType() {
        return "LIGHT_LOAD";
    }

    private Random random = new Random();

    @Override
    public void generateLoad(List<String> strings) {
        String filePath = "delete-" + random.nextGaussian(100, 10);
        try {
            createFileIfNotExists(filePath);
            writeDataToFile(strings, filePath);
            deleteFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private void writeDataToFile(List<String> data, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                //
            }
        }
    }
}

