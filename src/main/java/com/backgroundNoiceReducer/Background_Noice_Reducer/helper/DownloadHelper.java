package com.backgroundNoiceReducer.Background_Noice_Reducer.helper;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class DownloadHelper {
    private static final String PROCESSED_DIR = "C:\\Users\\sachin\\OneDrive\\Desktop\\Background_Noice_Reducer\\src\\main\\resources\\uploadedFiles";

    public ResponseEntity<?> downloadFile(String filename) {
        filename="processed-"+filename;
        try {
            Path filePath = Paths.get(PROCESSED_DIR, filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(404).body(filePath+"Resource not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
