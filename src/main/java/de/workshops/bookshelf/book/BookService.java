package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }
    List<Book> getAllBooks() {
        return repository.findAllBooks();
    }

    Book getBookByIsbn(String isbn) {
        return repository.findAllBooks().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    List<Book> getBooksByAuthor(String author) {
        return repository.findAllBooks().stream()
                .filter(book -> book.getAuthor().contains(author))
                .toList();
    }

    List<Book> searchBooks(BookSearchRequest searchRequest) {
        return repository.findAllBooks().stream()
                .filter(book -> book.getAuthor().contains(searchRequest.author())
                                || book.getTitle().contains(searchRequest.title()))
                .toList();
    }

}
