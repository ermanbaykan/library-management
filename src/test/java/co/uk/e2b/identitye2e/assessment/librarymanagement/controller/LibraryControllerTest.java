package co.uk.e2b.identitye2e.assessment.librarymanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LibraryControllerTest {

  @Autowired ObjectMapper mapper;
  @Autowired private MockMvc mvc;

  @BeforeEach()
  void setUp() throws Exception {

    mvc.perform(
        MockMvcRequestBuilders.delete("/book/test-isbn").accept(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldFind() throws Exception {

    var book =
        Book.builder()
            .isbn("test-isbn")
            .author("test-auth")
            .title("test-title")
            .publicationYear(2024)
            .build();

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mvc.perform(
            MockMvcRequestBuilders.get("/book?isbn=test-isbn").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mvc.perform(
            MockMvcRequestBuilders.get("/book?author=test-auth").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void shouldNotFind() throws Exception {

    mvc.perform(
            MockMvcRequestBuilders.get("/book?isbn=test-isbn").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    mvc.perform(
            MockMvcRequestBuilders.get("/book?author=test-auth").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldAdd() throws Exception {
    var book =
        Book.builder()
            .isbn("test-isbn")
            .author("test-auth")
            .title("test-title")
            .publicationYear(2024)
            .build();

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
  }

  @Test
  void shouldNotAddIfExist() throws Exception {
    var book =
        Book.builder()
            .isbn("test-isbn")
            .author("test-auth")
            .title("test-title")
            .publicationYear(2024)
            .build();

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldRemove() throws Exception {
    var book =
        Book.builder()
            .isbn("test-isbn")
            .author("test-auth")
            .title("test-title")
            .publicationYear(2024)
            .build();

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mvc.perform(MockMvcRequestBuilders.delete("/book/test-isbn").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void removeShouldNotReturnOkIfNotExisted() throws Exception {
    var book =
        Book.builder()
            .isbn("test-isbn")
            .author("test-auth")
            .title("test-title")
            .publicationYear(2024)
            .build();

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mvc.perform(MockMvcRequestBuilders.delete("/book/test-isbn").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mvc.perform(MockMvcRequestBuilders.delete("/book/test-isbn").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnIncrementedMessage() throws Exception {
    var book =
        Book.builder()
            .isbn("test-isbn")
            .author("test-auth")
            .title("test-title")
            .publicationYear(2024)
            .build();

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mvc.perform(
            MockMvcRequestBuilders.patch("/book/return/test-isbn")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("INCREMENTED"));
  }

  @Test
  void shoudlReturnDecrementedMessage() throws Exception {
    var book =
        Book.builder()
            .isbn("test-isbn")
            .author("test-auth")
            .title("test-title")
            .publicationYear(2024)
            .build();

    mvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mvc.perform(
            MockMvcRequestBuilders.patch("/book/borrow/test-isbn")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("DECREMENTED"));
  }
}
