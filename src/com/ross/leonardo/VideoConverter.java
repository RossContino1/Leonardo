/*
 * Leonardo - Media Conversion Tool
 * Copyright (c) 2026 Ross Contino
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

package com.ross.leonardo;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VideoConverter extends SwingWorker<Void, Integer> {

    private final String input;
    private final String output;
    private final Preset preset;
    private final JProgressBar progressBar;
    private final JButton convertButton;
    private final JFrame parent;
    private Exception conversionError = null;
    private Process ffmpegProcess;
    private String ffmpegLog = "";

    public VideoConverter(String input,
                          String output,
                          Preset preset,
                          JProgressBar progressBar,
                          JButton convertButton,
                          JFrame parent) {

        this.input = input;
        this.output = output;
        this.preset = preset;
        this.progressBar = progressBar;
        this.convertButton = convertButton;
        this.parent = parent;
    }

    @Override
    protected Void doInBackground() {

        try {
            double duration = FFmpegUtil.getDurationSeconds(input);

            List<String> command = new ArrayList<>();
            command.add("ffmpeg");
            command.add("-hide_banner");
            command.add("-y");
            command.add("-i");
            command.add(input);

            if (preset != null && preset.getFfmpegArgs() != null) {
                command.addAll(preset.getFfmpegArgs());
            }

            command.add(output);

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            ffmpegProcess = pb.start();
            Process process = ffmpegProcess;

            StringBuilder logBuilder = new StringBuilder();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String line;

                while (!isCancelled() && (line = reader.readLine()) != null) {
                    logBuilder.append(line).append(System.lineSeparator());

                    if (line.contains("time=") && duration > 0) {
                        double current = FFmpegUtil.extractTimeInSeconds(line);
                        int percent = (int) ((current / duration) * 100);
                        publish(Math.min(percent, 100));
                    }
                }
            }

            int exitCode = process.waitFor();
            ffmpegLog = logBuilder.toString();

            if (isCancelled()) {
                return null;
            }

            File outFile = new File(output);

            if (exitCode != 0) {
                throw new RuntimeException("FFmpeg exited with code " + exitCode + ".\n\n" + ffmpegLog);
            }

            if (!outFile.exists() || outFile.length() == 0) {
                throw new RuntimeException("FFmpeg finished but no output file was created.\n\n" + ffmpegLog);
            }

        } catch (Exception e) {
            conversionError = e;
        }

        return null;
    }

    @Override
    protected void process(List<Integer> chunks) {
        if (chunks == null || chunks.isEmpty()) {
            return;
        }

        int value = chunks.get(chunks.size() - 1);
        progressBar.setValue(value);
    }

    @Override
    protected void done() {

        convertButton.setEnabled(true);

        if (parent instanceof MainWindow) {
            ((MainWindow) parent).conversionFinished();
        }

        if (isCancelled()) {

            if (ffmpegProcess != null) {
                ffmpegProcess.destroyForcibly();
            }

            progressBar.setValue(0);

            JOptionPane.showMessageDialog(parent,
                    "Conversion Cancelled.",
                    "Leonardo 10",
                    JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        if (conversionError != null) {
            String message = conversionError.getMessage();

            if (message == null || message.isBlank()) {
                message = "Unknown FFmpeg error.";
            }

            if (message.length() > 2000) {
                message = message.substring(0, 2000) + "\n\n...output truncated...";
            }

            JOptionPane.showMessageDialog(parent,
                    "Conversion failed:\n\n" + message,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        progressBar.setValue(100);

        JOptionPane.showMessageDialog(parent,
                "Conversion Complete!",
                "Leonardo 10",
                JOptionPane.INFORMATION_MESSAGE);
    }
}