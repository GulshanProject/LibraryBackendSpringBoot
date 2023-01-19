package com.example.StudentLibrary.repositories;

import com.example.StudentLibrary.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
}
