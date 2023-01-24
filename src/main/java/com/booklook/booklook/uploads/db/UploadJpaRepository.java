package com.booklook.booklook.uploads.db;

import com.booklook.booklook.uploads.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadJpaRepository extends JpaRepository<Upload, Long> {
}
