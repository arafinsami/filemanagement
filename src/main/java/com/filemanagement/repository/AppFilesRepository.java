package com.filemanagement.repository;

import com.filemanagement.entity.AppFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFilesRepository extends JpaRepository<AppFiles, Long> {
}
