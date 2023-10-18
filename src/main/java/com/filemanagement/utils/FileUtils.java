package com.filemanagement.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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

    protected void upload(MultipartFile file, String filepath) {
        String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File dir = new File(filepath);
                File serverFile = new File(dir + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                logger.error("file uploading failed !!!!");
            }
        }
    }
}
