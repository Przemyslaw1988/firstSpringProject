package com.example.bookpz.uploads.application;

import com.example.bookpz.uploads.application.ports.UploadUseCase;
import com.example.bookpz.uploads.db.UpdateJpaRepository;
import com.example.bookpz.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UploadService implements UploadUseCase {

    private final UpdateJpaRepository repository;

    @Override
    public Upload save(SaveUploadCommand command) {
        Upload upload = new Upload(
                command.getFilename(),
                command.getContentType(),
                command.getFile()
                );


        repository.save(upload);
        return upload;
    }

    @Override
    public Optional<Upload> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }
}
