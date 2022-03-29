package com.ssafy.core.repository;

import com.ssafy.core.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepoCommon{
}
