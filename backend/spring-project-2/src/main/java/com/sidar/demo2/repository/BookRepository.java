package com.sidar.demo2.repository;

import com.sidar.demo2.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE " +
            "(?1 IS NULL OR ?1 = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', ?1, '%'))) AND " +
            "(?2 IS NULL OR ?2 = '' OR LOWER(b.author) LIKE LOWER(CONCAT('%', ?2, '%')))")
    Page<Book> findByTitleAndAuthor(@Param("title") String title, @Param("author") String author, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);
}