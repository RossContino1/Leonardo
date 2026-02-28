/*
 * Leonardo - Media Conversion Tool
 * Copyright (c) 2026 Ross Contino
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

package com.ross.leonardo;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class VideoConverter extends SwingWorker<Void, Integer> {

    private final String input;
    private final String output;
    private final Preset preset;                 // ✅ NEW
    private final JProgressBar progressBar;
    private final JButton convertButton;
    private final JFrame parent;
    private Exception conversionError = null;
    private Process ffmpegProcess;

    public VideoConverter(String input,
                          String output,
                          Preset preset,          // ✅ NEW
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
            command.add("-y");               // overwrite output if it exists
            command.add("-i");
            command.add(input);

            // ✅ APPLY THE PRESET FFmpeg ARGS (THIS IS THE FIX)
            if (preset != null && preset.getFfmpegArgs() != null) {
                command.addAll(preset.getFfmpegArgs());
            }

            command.add(output);

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            ffmpegProcess = pb.start();
            Process process = ffmpegProcess;

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while (!isCancelled() && (line = reader.readLine()) != null) {

                if (line.contains("time=")) {
                    double current = FFmpegUtil.extractTimeInSeconds(line);
                    int percent = (int) ((current / duration) * 100);
                    publish(Math.min(percent, 100));
                }
            }

            process.waitFor();

        } catch (Exception e) {
            conversionError = e;
        }

        return null;
    }

    @Override
    protected void process(List<Integer> chunks) {
        int value = chunks.get(chunks.size() - 1);
        progressBar.setValue(value);
    }

    @Override
    protected void done() {

        convertButton.setEnabled(true);

        // Re-enable preset & reset cancel in MainWindow
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
            JOptionPane.showMessageDialog(parent,
                    "Conversion failed:\n" + conversionError.getMessage(),
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