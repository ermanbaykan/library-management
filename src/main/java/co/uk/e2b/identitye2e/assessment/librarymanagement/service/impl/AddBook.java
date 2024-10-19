package co.uk.e2b.identitye2e.assessment.librarymanagement.service.impl;


import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.AddItem;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;

@Component
public class AddBook implements AddItem<Book> {

  public List<Book> add(List<Book> bookList, Book newBook) {
    CopyOnWriteArrayList<Book> newList = new CopyOnWriteArrayList<>(bookList);
    if (newBook.getTitle().isEmpty() || newBook.getIsbn().isEmpty()) {
      return newList;
    }
    newList.add(newBook);
    return newList;
  }

}
