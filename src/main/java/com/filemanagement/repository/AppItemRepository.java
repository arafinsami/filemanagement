package com.filemanagement.repository;

import com.filemanagement.entity.AppItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppItemRepository extends JpaRepository<AppItem, Long> {

    public Optional<AppItem> findByName(String name);
}
