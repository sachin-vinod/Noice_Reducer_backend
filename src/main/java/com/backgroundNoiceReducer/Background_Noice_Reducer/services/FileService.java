package com.backgroundNoiceReducer.Background_Noice_Reducer.services;

import com.backgroundNoiceReducer.Background_Noice_Reducer.entities.FileEntity;

public interface FileService {
    public FileEntity saveFile(FileEntity file);
    public FileEntity getFile(Long fileId);
}
