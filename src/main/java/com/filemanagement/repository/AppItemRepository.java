package com.filemanagement.repository;

import com.filemanagement.entity.AppItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppItemRepository extends JpaRepository<AppItem, Long> {
}
