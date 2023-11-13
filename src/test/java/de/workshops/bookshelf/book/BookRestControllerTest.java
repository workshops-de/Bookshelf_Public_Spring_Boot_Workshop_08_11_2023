package de.workshops.bookshelf.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRestControllerTest {

    @Mock
    BookService service;

    @InjectMocks
    BookRestController controller;

    @BeforeEach
    void init()
    {
        service = Mockito.mock(BookService.class);
        controller = new BookRestController(service);
    }

    @Test
    void shouldGetAllBooks(){
        when(service.getAllBooks()).thenReturn(List.of(new Book(), new Book(), new Book()));

        final var allBooks = controller.getAllBooks();

        assertThat(allBooks).hasSize(3);
    }

}