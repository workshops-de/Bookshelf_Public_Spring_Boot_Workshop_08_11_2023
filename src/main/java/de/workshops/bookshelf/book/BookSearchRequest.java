package de.workshops.bookshelf.book;

import jakarta.validation.constraints.NotBlank;

record BookSearchRequest(@NotBlank String author, @NotBlank String title) {
}
