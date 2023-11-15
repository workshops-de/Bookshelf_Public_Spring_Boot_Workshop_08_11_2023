package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
//    private final BookRepository repository;
//    private final BookJdbcRepository repository;
    private final BookJpaRepository repository;

    public BookService(BookJpaRepository repository) {
        this.repository = repository;
    }
    List<Book> getAllBooks() {
        return repository.findAll();
    }

    Book getBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
//        return repository.findAllBooks().stream()
//                .filter(book -> book.getIsbn().equals(isbn))
//                .findFirst()
//                .orElse(null);
    }

    List<Book> getBooksByAuthor(String author) {
        return repository.findByAuthorContaining(author);
//        return repository.findAllBooks().stream()
//                .filter(book -> book.getAuthor().contains(author))
//                .toList();
    }

    List<Book> searchBooks(BookSearchRequest searchRequest) {
        return repository.findByTitleContainingOrAuthorContaining(searchRequest.title(), searchRequest.author());
//        return repository.findAllBooks().stream()
//                .filter(book -> book.getAuthor().contains(searchRequest.author())
//                                || book.getTitle().contains(searchRequest.title()))
//                .toList();
    }

    public Book createBook(Book book) {
        return repository.save(book);
    }
}
