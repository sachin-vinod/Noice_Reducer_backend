package com.backgroundNoiceReducer.Background_Noice_Reducer.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class UploadProcessHelper {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${processed.dir}")
    private String processedDir;

    private final ProcessFileHelper processFileHelper;

    public UploadProcessHelper(ProcessFileHelper processFileHelper) {
        this.processFileHelper = processFileHelper;
    }

    public ResponseEntity<?> uploadProcess(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String uniqueId = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String tempFileName = uniqueId + fileExtension;
        String processedFileName = "processed-" + uniqueId + fileExtension;

        try {
            // Ensure the upload directory exists
            Path uploadDirPath = Paths.get(uploadDir);
            if (Files.notExists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }

            // Create temporary file for uploading
            Path tempFilePath = uploadDirPath.resolve(tempFileName);

            // Save uploaded file to the temporary path
            Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Ensure the processed directory exists
            Path processedDirPath = Paths.get(processedDir);
            if (Files.notExists(processedDirPath)) {
                Files.createDirectories(processedDirPath);
            }

            // Define path for the processed file
            Path processedFilePath = processedDirPath.resolve(processedFileName);

            // Process the file
            processFileHelper.processFile(tempFilePath.toString(), processedFilePath.toString());

            // Return the processed file to the user
            byte[] fileBytes = Files.readAllBytes(processedFilePath);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileBytes));

            // Delete the temporary and processed files
            Files.delete(tempFilePath);
            Files.delete(processedFilePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + processedFileName + "\"")
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }
}
