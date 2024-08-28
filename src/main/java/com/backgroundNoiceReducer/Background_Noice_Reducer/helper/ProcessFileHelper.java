package com.backgroundNoiceReducer.Background_Noice_Reducer.helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ProcessFileHelper {
    public void processFile(String inputFilePath, String outputFilePath) throws IOException {
        //Below method is used to format the command string by inserting the input and output file paths
        //%s: These are placeholders for the input and output file paths.
        String command = String.format("ffmpeg -i \"%s\" -af \"lowpass=3000,highpass=200,afftdn=nf=-25\" \"%s\"", inputFilePath, outputFilePath);

        //Below method executes the command in the operating system's command line.
        Process process = Runtime.getRuntime().exec(command);
        System.out.println("Executing command>>>>>>: " + command);

        // Wait for the process to complete
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Error processing file, exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new IOException("Process was interrupted", e);
        }
    }

}
