package com.proj.demo.mapper;

import com.proj.demo.dto.BookDto;
import com.proj.demo.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book mapToBook(BookDto bookDto) {
        Book book=new Book();
        book.setTitle(bookDto.title());
        book.setSummary(bookDto.summary());
        return book;
    }
}
