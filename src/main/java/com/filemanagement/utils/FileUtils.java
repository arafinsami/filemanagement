package com.filemanagement.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

@Slf4j
public abstract class FileUtils {

    @Value("${baseDirectory}")
    public String baseDirectory;
    Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public void createSpace(String folderName) {
        File folder = new File(baseDirectory, folderName);
        if (folder.exists()) {
            logger.info("Folder already exists.");
        } else {
            boolean folderCreated = folder.mkdir();
            if (folderCreated) {
                logger.info("Folder created successfully.");
            } else {
                logger.info("Folder created successfully.");
            }
        }
    }

    public void updateSpace(String folderName, String newFolderName) {
        File folder = new File(baseDirectory, folderName);
        if (folder.exists()) {
            File newFolder = new File(baseDirectory, newFolderName);
            if (folder.renameTo(newFolder)) {
                logger.info("Folder created successfully.");
            } else {
                logger.info("Folder update the folder.");
            }
        } else {
            logger.info("Folder not found.");
        }
    }
}
