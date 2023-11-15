package de.workshops.bookshelf.book;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {
    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("{isbn}")
    ResponseEntity<Book> getBookByIsbn(@PathVariable @Size(max=14) String isbn) {
        final var foundBook = service.getBookByIsbn(isbn);

        if (foundBook == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foundBook);
    }

    @GetMapping(params = "author")
    List<Book> getBooksByAuthor(@RequestParam @NotBlank String author) {
        return service.getBooksByAuthor(author);
    }

    @PostMapping("search")
    List<Book> searchBooks(@RequestBody @Valid BookSearchRequest searchRequest) {
        return service.searchBooks(searchRequest);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    Book createBook(@RequestBody Book aNewBook) {
        return service.createBook(aNewBook);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail error(ConstraintViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Book Not Found");
        problemDetail.setType(URI.create("http://localhost:8080/constraint_exception.html"));
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}
