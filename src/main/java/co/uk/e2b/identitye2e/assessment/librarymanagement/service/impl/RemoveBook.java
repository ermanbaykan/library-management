package co.uk.e2b.identitye2e.assessment.librarymanagement.service.impl;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.RemoveItem;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;

@Component
public class RemoveBook implements RemoveItem<Book> {

  @Override
  public List<Book> remove(List<Book> bookList, Book book) {
    CopyOnWriteArrayList<Book> newList = new CopyOnWriteArrayList<>(bookList);
    newList.remove(book);
    return newList;
  }
}
