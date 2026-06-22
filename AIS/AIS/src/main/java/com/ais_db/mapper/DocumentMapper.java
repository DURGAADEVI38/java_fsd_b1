package com.ais_db.mapper;

import com.ais_db.dto.DocumentDto;
import com.ais_db.model.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public static DocumentDto toDto(Document d) {
        return new DocumentDto(
                d.getFileName(),
                d.getFilePath()
        );
    }
}
