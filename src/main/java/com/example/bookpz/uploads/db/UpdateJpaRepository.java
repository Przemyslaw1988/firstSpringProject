package com.example.bookpz.uploads.db;

import com.example.bookpz.uploads.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateJpaRepository extends JpaRepository<Upload, Long> {
}
