package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Import(BookJdbcRepository.class)
class BookJdbcRepositoryTest {
    @Autowired
    BookJdbcRepository repository;

    @Test
    void shouldFindAllBooks() {
        final var allBooks = repository.findAllBooks();
        assertThat(allBooks).hasSize(3);
    }
}