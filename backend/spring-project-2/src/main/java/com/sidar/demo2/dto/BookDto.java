package com.sidar.demo2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookDto {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, message = "Title must have at least 2 characters")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @Min(value = 0, message = "Year must be positive")
    private int year;

    // Getter-Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
