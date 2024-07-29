package com.backgroundNoiceReducer.Background_Noice_Reducer.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class UploadProcessHelper {

    private static final String UPLOAD_DIR = "C:\\Users\\sachin\\OneDrive\\Desktop\\Background_Noice_Reducer\\src\\main\\resources\\uploadedFiles";
    @Autowired
    ProcessFileHelper processFileHelper;

    public ResponseEntity<?> uploadProcess(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        try {
            // Create a temporary file for processing
            Path tempFilePath = Files.createTempFile("upload-", originalFileName);

            // Save the uploaded file to the temporary path
            Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Define the path for the processed file
            Path processedFilePath = Paths.get("C:\\Users\\sachin\\OneDrive\\Desktop\\Background_Noice_Reducer\\src\\main\\resources\\uploadedFiles\\processed-" + originalFileName);

            // Process the file to reduce noise
            processFileHelper.processFile(tempFilePath.toString(), processedFilePath.toString());

            // Optionally, delete the temporary file after processing
            Files.delete(tempFilePath);

           return ResponseEntity.ok("File processed successfully. " );
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }

}
