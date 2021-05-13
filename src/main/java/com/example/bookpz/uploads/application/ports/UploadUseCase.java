package com.example.bookpz.uploads.application.ports;

import com.example.bookpz.uploads.domain.Upload;
import lombok.Value;

import java.util.Optional;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);

    Optional<Upload> findById(Long id);

    void removeById(Long id);

    @Value
    class SaveUploadCommand {

        byte[] file;
        String contentType;
        String filename;
    }


}
