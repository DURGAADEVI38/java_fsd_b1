package com.proj.demo.controller;

import com.proj.demo.dto.BookDto;
import com.proj.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    @PostMapping("/api/create/{id}")
    public void createBook(@PathVariable int id,
                           @Valid @RequestBody BookDto bookDto)
    {
        bookService.create(id,bookDto);
    }
}
