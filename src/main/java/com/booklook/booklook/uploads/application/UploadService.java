package com.booklook.booklook.uploads.application;

import com.booklook.booklook.uploads.application.port.UploadUseCase;
import com.booklook.booklook.uploads.db.UploadJpaRepository;
import com.booklook.booklook.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class UploadService implements UploadUseCase {

    private final UploadJpaRepository repository;

    @Override
    public Upload save(SaveUploadCommand command) {

        Upload upload = new Upload(
                command.getFilename(),
                command.getContentType(),
                command.getFile()
        );
        repository.save(upload);
        log.info("Upload saved: " + upload.getFilename() + " with id: " + upload.getId());
        return upload;
    }

    @Override
    public Optional<Upload> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }
}
