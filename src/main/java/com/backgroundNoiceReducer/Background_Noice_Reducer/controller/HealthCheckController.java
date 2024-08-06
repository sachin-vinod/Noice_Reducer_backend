package com.backgroundNoiceReducer.Background_Noice_Reducer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("/ffmpeg-version")
    public String getFfmpegVersion() {
        try {
            Process process = Runtime.getRuntime().exec("ffmpeg -version");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder version = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                version.append(line).append("\n");
            }
            process.waitFor();
            return version.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
