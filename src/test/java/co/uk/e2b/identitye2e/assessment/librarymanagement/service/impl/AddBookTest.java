package co.uk.e2b.identitye2e.assessment.librarymanagement.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddBookTest {

  ArrayList<Book> books;
  AddBook addBook;

  @BeforeEach
  void setUp() {
    addBook = new AddBook();
    books = new ArrayList<>();
    books.addAll(
        List.of(Book.builder().title("book1").isbn("ISBN1").author("auth1").build(),
            Book.builder().title("book2").isbn("ISBN1").author("auth2").build()));
  }

  @Test
  void shouldAddBook() {
    var newBook = Book.builder().title("book3").isbn("ISBN3").author("auth3").build();
    var t = addBook.add(books, newBook);
    assertEquals(3, t.size());
  }

  @Test
  void shouldNotAddBookIfTitleIsEmpty() {
    var newBook = Book.builder().isbn("ISBN3").title("").author("auth3").build();
    assertEquals(2, books.size());
    var t = addBook.add(books, newBook);
    assertEquals(2, t.size());
  }

  @Test
  void shouldNotAddBookIfISBNIsEmpty() {
    var newBook = Book.builder().isbn("").title("book3").author("auth3").build();
    assertEquals(2, books.size());
    var t = addBook.add(books, newBook);
    assertEquals(2, t.size());
  }
}