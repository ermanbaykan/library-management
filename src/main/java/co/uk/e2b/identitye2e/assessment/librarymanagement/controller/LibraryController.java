package co.uk.e2b.identitye2e.assessment.librarymanagement.controller;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Response;
import co.uk.e2b.identitye2e.assessment.librarymanagement.exception.BookNotFound;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.Library;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

  final Library libraryService;

  public LibraryController(Library libraryService) {
    this.libraryService = libraryService;
  }


  @GetMapping("/book")
  public ResponseEntity<Response> findBy(
      @RequestParam(value = "isbn", required = false) String isbn,
      @RequestParam(value = "author", required = false) String author) {

    if ((author == null && isbn == null) || (author != null && isbn != null)) {
      return null;
    }

    if (isbn != null && !isbn.isEmpty()) {
      return ResponseEntity.ok()
          .body(
              Response.builder().books(List.of(libraryService.findBookByISBN(isbn))).build());
    }
    if (author != null && !author.isEmpty()) {
      libraryService.findBooksByAuthor(author);
      return ResponseEntity.ok()
          .body(Response.builder().books(libraryService.findBooksByAuthor(author)).build());
    }

    throw new BookNotFound("Book not found");
  }

  @PostMapping("/book")
  public ResponseEntity<Response> add(@RequestBody Book book) {
    return ResponseEntity.created(URI.create("/book/" + book.getIsbn()))
        .body(Response.builder().message((libraryService.addBook(book).toString())).build());
  }

  @DeleteMapping("/book/{isbn}")
  public ResponseEntity<Response> remove(@PathVariable("isbn") String isbn) {

    return ResponseEntity.ok()
        .body(Response.builder().message(libraryService.removeBook(isbn).toString()).build());
  }

  @PatchMapping("/book/return/{isbn}")
  public ResponseEntity<Response> returnBook(@PathVariable String isbn) {
    return ResponseEntity.ok()
        .body(Response.builder().message(libraryService.returnBook(isbn).toString()).build());
  }

  @PatchMapping("/book/borrow/{isbn}")
  public ResponseEntity<Response> borrowBook(@PathVariable String isbn) {
    return ResponseEntity.ok()
        .body(Response.builder().message(libraryService.borrowBook(isbn).toString()).build());
  }

}
