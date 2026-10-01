package com.Blog.Blog.services.impl;

import com.Blog.Blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // Original File Name
        String originalFileName = file.getOriginalFilename();

        // Extract File Extension
        String extension = "";

        int lastIndex = originalFileName.lastIndexOf(".");

        if (lastIndex != -1) {
            extension = originalFileName.substring(lastIndex);
        }

        // Generate Random UUID File Name
        String randomId = UUID.randomUUID().toString();

        String fileName1 = randomId + extension;

        // Full Path
        String filePath = path + File.separator + fileName1;

        // Create Folder if not exists
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Copy File
        Files.copy(file.getInputStream(),
                Paths.get(filePath),
                StandardCopyOption.REPLACE_EXISTING);

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath = path + File.separator + fileName;

        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }
}