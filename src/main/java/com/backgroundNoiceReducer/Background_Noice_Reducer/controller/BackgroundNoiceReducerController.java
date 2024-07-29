package com.backgroundNoiceReducer.Background_Noice_Reducer.controller;

import com.backgroundNoiceReducer.Background_Noice_Reducer.helper.DownloadHelper;
import com.backgroundNoiceReducer.Background_Noice_Reducer.helper.UploadProcessHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BackgroundNoiceReducerController {

    @Autowired
    UploadProcessHelper uploadHelper;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        return UploadProcessHelper.uploadProcess(file);
    }
}
