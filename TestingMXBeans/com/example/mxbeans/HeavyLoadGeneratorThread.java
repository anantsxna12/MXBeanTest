package com.example.mxbeans;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeavyLoadGeneratorThread extends LoadGeneratorThread {
    @Override
    public String getThreadType() {
        return "HEAVY_LOAD";
    }

    @Override
    protected void generateLoad(List<String> strings) {
        String regexPattern = "a*";
        Pattern pattern = Pattern.compile(regexPattern);

        // Iterate through the list of strings and perform regex queries
        for (String str : strings) {
            Matcher matcher = pattern.matcher(str);
            boolean matchFound = matcher.find();
            if (matchFound) {
                // Do something with the matched result, if needed

            }
        }
    }

//    @Override
//    protected void generateLoad(List<String> strings) {
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            // Perform heavy I/O operations by writing a large amount of data
//            for (int i = 0; i < 10000; i++) {
//                String data = "This is heavy load data line " + i;
//                writer.write(data);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//                // Generate random limits for integration
//        double lowerLimit = random.nextDouble() * 10; // Adjust the range as needed
//        double upperLimit = lowerLimit + random.nextDouble() * 10; // Adjust the range as needed
//
//        // Calculate the integral between a sine and cosine curve
//        double integral = calculateIntegral(lowerLimit, upperLimit);
//    }
//
//    private double calculateIntegral(double lowerLimit, double upperLimit) {
//        int numSegments = 10000; // Number of segments for the trapezoidal rule
//        double delta = (upperLimit - lowerLimit) / numSegments;
//        double sum = 0.0;
//
//        for (int i = 0; i <= numSegments; i++) {
//            double x = lowerLimit + i * delta;
//            double y = Math.sin(x) * Math.cos(x);
//            if (i == 0 || i == numSegments) {
//                sum += y;
//            } else {
//                sum += 2 * y;
//            }
//        }
//
//        return 0.5 * delta * sum;
//    }
}
