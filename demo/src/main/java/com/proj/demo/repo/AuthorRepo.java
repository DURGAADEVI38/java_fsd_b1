package com.proj.demo.repo;

import com.proj.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepo extends JpaRepository<Author,Integer> {
    List<Author> id(int id);
}
