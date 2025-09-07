package com.sidar.demo2.controller;

import com.sidar.demo2.model.Book;
import com.sidar.demo2.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.sidar.demo2.dto.BookDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<Book> getBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        if (search != null && !search.trim().isEmpty()) {
            return bookService.searchBooks(search, search, pageable);
        }

        if ((title != null && !title.trim().isEmpty()) || (author != null && !author.trim().isEmpty())) {
            return bookService.searchBooks(title, author, pageable);
        }

        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/search")
    public Page<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String query,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        if (query != null && !query.trim().isEmpty()) {
            return bookService.searchBooks(query, query, pageable);
        }

        return bookService.searchBooks(title, author, pageable);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @PostMapping
    public Book createBook(@Valid @RequestBody BookDto bookDto) {
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .year(bookDto.getYear())
                .build();
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .year(bookDto.getYear())
                .build();
        return bookService.updateBook(id, book);
    }
}