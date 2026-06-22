package com.ais_db.util;
import com.ais_db.exception.FileInvalidExtenstionException;
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
        String filename = file.getOriginalFilename();
        String ext = filename.split("\\.")[1];

        if(!allowedExts.contains(ext))
            throw new FileInvalidExtenstionException(ext + " not allowed");

    }
}
