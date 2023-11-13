package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
class BookRestControllerMockBeanTest {

    @MockBean
    BookService service;

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> isbnCaptor;

    @Test
    void shouldGetBootByIsbn_OK() throws Exception {
        when(service.getBookByIsbn(isbnCaptor.capture())).thenReturn(new Book());

        mockMvc.perform(get("/book/1234567890"))
                .andDo(print())
                .andExpect(status().isOk());

        final var isbnCaptorValue = isbnCaptor.getValue();
        assertThat(isbnCaptorValue).isEqualTo("1234567890");
    }

    @Test
    void shouldGetBootByIsbn_NO_CONTENT() throws Exception {
        when(service.getBookByIsbn(anyString())).thenReturn(null);

        mockMvc.perform(get("/book/1234567890"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}