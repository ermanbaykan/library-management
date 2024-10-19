package co.uk.e2b.identitye2e.assessment.librarymanagement.service;

import java.util.List;

public interface FindBy<T>{

  T find(List<T> items, String value);
}
