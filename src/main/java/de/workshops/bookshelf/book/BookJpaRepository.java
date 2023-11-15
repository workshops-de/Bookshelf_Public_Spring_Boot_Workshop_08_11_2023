package de.workshops.bookshelf.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);

    List<Book> findByAuthorContaining(String author);

    List<Book> findByTitleContainingOrAuthorContaining(String title, String author);

    @Query("select b from Book b where b.title like ?1 or b.author like ?2")
    List<Book> findBySearchRequest(String title, String author);

}
