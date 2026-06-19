package com.proj.demo.util;

import com.proj.demo.exception.FileInvalidExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    public static void validateFile(MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            throw new FileNotFoundException("Please select a file");
        }
        List<String> allowedExts = List.of("png", "jpeg", "jpg", "pdf", "docx", "pages");
        // Exact the extension of uploaded file
        String filename = file.getOriginalFilename(); //pan.jpeg
        String ext = filename.split("\\.")[1];

        if(!allowedExts.contains(ext))
            throw new FileInvalidExtensionException(ext + " not allowed");

    }
}
