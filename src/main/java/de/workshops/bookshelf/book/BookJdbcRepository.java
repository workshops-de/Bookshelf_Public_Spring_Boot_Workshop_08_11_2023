package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
class BookJdbcRepository {

//    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate template;

    BookJdbcRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    List<Book> findAllBooks() {
        var query = "select * from book";
        return template.query(query, new BeanPropertyRowMapper<>(Book.class));
    }


    public Book addBook(Book book) {
        var query = "INSERT INTO book (isbn, title, author, description) VALUES (:isbn, :title, :author, :description)";
        final var params = Map.of("isbn", book.getIsbn(),
                "title", book.getTitle(),
                "author", book.getAuthor(),
                "description", book.getDescription()
        );
        template.update(query, params);
        return book;
    }
}
