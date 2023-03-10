package de.uni.koeln.se.bookstore.controller;

import de.uni.koeln.se.bookstore.datamodel.Book;
import de.uni.koeln.se.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequestMapping("/bookStore")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    BookService bookSer;


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        List<Book> books = new ArrayList<Book>();
        books = bookSer.findBooks();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {

        return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookSer.addBook(book);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> removeBookId(@PathVariable int id) {

        Book book = bookSer.fetchBook(id).get();
        if (bookSer.deleteBook(id)) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/oldestBook")
    public ResponseEntity<Book> getOldestBook() {
        List<Book> books = bookSer.findBooks();

        books.sort(Comparator.comparing(Book::getDateYear));

        return new ResponseEntity<>(books.get(0), HttpStatus.OK);
    }

    @GetMapping("/mostRecentBook")
    public ResponseEntity<Book> getMostRecentBook() {
        List<Book> books = bookSer.findBooks();

        books.sort(Comparator.comparing(Book::getDateYear).reversed());

        return new ResponseEntity<>(books.get(0), HttpStatus.OK);
    }

}
