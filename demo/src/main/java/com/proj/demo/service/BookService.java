package com.proj.demo.service;

import com.proj.demo.dto.BookDto;
import com.proj.demo.mapper.BookMapper;
import com.proj.demo.model.Author;
import com.proj.demo.model.Book;
import com.proj.demo.repo.AuthorRepo;
import com.proj.demo.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    private final BookMapper bookMapper;
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    public void create(int id, BookDto bookDto) {
        Author author=authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        Book book=bookMapper.mapToBook(bookDto);
        bookRepo.save(book);

    }
}
