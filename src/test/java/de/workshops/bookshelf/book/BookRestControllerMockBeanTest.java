package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMockBeanTest {

    @MockBean
    BookService service;

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> isbnCaptor;

    @Test
    @WithMockUser
    void shouldGetBootByIsbn_OK() throws Exception {
        when(service.getBookByIsbn(isbnCaptor.capture())).thenReturn(new Book());

        mockMvc.perform(get("/book/1234567890"))
                .andDo(print())
                .andExpect(status().isOk());

        final var isbnCaptorValue = isbnCaptor.getValue();
        assertThat(isbnCaptorValue).isEqualTo("1234567890");
    }

    @Test
    @WithMockUser
    void shouldGetBootByIsbn_NO_CONTENT() throws Exception {
        when(service.getBookByIsbn(anyString())).thenReturn(null);

        mockMvc.perform(get("/book/1234567890"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
//    @WithMockUser(roles = {"ADMIN"})
    @WithMockUser
    void shouldCreateBook() throws Exception {
        when(service.createBook(any(Book.class))).thenReturn(new Book());

        var isbn = "1111111111";
        var title = "My first book";
        var author = "Birgit Kratz";
        var description = "A guide to SpringBoot";

        mockMvc.perform(post("/book")
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "author": "%s",
                                    "description": "%s"
                                }""".formatted(isbn, title, author, description))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

}