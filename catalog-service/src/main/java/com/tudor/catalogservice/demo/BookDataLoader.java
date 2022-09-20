package com.tudor.catalogservice.demo;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.tudor.catalogservice.domain.Book;
import com.tudor.catalogservice.domain.BookRepository;

@Component
@Profile("testdata")
public class BookDataLoader {
  private final BookRepository bookRepository;

  Logger logger = LoggerFactory.getLogger(BookDataLoader.class);

  public BookDataLoader(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void loadBookTestData() {
    var book1 = Book.of("1234567891", "Northern Lights",
      "Lyra Silverstar", 9.90);

    var book2 = Book.of("1234567892", "Polar Journey",
      "Iorek Polarson", 12.90);

    bookRepository.saveAll(List.of(book1,book2));
  }
}
