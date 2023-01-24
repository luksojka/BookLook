package com.booklook.booklook.uploads.application.port;

import com.booklook.booklook.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

public interface UploadUseCase {
    Upload save(SaveUploadCommand command);

    Optional<Upload> getById(Long id);

    void removeById(Long id);

    @Value
    @AllArgsConstructor
    class SaveUploadCommand {
        String filename;
        byte[] file;
        String contentType;
    }
}
