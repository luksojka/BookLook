package com.booklook.booklook.uploads.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Upload {

    @Id
    @GeneratedValue
    private Long id;
    private byte[] file;
    private String contentType;
    private String filename;

    @CreatedDate
    private LocalDateTime createdAt;

    public Upload(String filename, String contentType, byte[] file) {
        this.filename = filename;
        this.contentType = contentType;
        this.file = file;
    }
}
