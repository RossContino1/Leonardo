/*
 * Leonardo - Media Conversion Tool
 * Copyright (c) 2026 Ross Contino
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

package com.ross.leonardo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FFmpegUtil {

    public static boolean isFFmpegAvailable() {
        try {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-version");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasEncoder(String encoderName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-encoders");
            pb.redirectErrorStream(true);

            Process process = pb.start();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();

                    if (trimmed.matches("^[VASFS\\.]{6}\\s+" + java.util.regex.Pattern.quote(encoderName) + "\\b.*$")) {
                        process.waitFor();
                        return true;
                    }
                }
            }

            process.waitFor();
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static double getDurationSeconds(String filePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", filePath);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Duration:")) {
                        String duration = line.split("Duration:")[1].split(",")[0].trim();
                        return parseDuration(duration);
                    }
                }
            }

            process.waitFor();
        } catch (Exception ignored) {
        }

        return 1;
    }

    private static double parseDuration(String duration) {
        String[] parts = duration.split(":");
        double hours = Double.parseDouble(parts[0]);
        double minutes = Double.parseDouble(parts[1]);
        double seconds = Double.parseDouble(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

    public static double extractTimeInSeconds(String line) {
        try {
            int index = line.indexOf("time=");
            if (index == -1) {
                return 0;
            }

            String rest = line.substring(index + 5).trim();
            String[] tokens = rest.split("\\s+");
            if (tokens.length == 0) {
                return 0;
            }

            return parseDuration(tokens[0]);
        } catch (Exception e) {
            return 0;
        }
    }
}