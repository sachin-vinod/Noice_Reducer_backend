package com.backgroundNoiceReducer.Background_Noice_Reducer.controller;

import com.backgroundNoiceReducer.Background_Noice_Reducer.helper.UploadProcessHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@CrossOrigin(origins = "*")
public class BackgroundNoiceReducerController {

    @Autowired
    UploadProcessHelper uploadHelper;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        return uploadHelper.uploadProcess(file);
    }

    @GetMapping("/ffmpeg-version")
    public String getFfmpegVersion() {
        try {
            return "ffmpeg version is hereeeeeeeeeeeee";
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
