package api;

import api.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookServiceImpl bookServiceImpl;

    @PostMapping("/addBook")
    ResponseEntity<Object> addBook(@RequestBody Book book){
        return bookServiceImpl.addBook(book);
    }

    @GetMapping("/books")
    List<Book> getBooks(){
        return bookServiceImpl.getAllBooks();
    }

    @PutMapping("/updateBook")
    ResponseEntity<Object> updateBook(@RequestBody Book newBook,@RequestParam Long id){
        return bookServiceImpl.updateBook(newBook, id);
    }

    @GetMapping("/deleteBook")
    ResponseEntity<Object> deleteBook(@RequestParam Long id){
        return bookServiceImpl.deleteBook(id);
    }

    @GetMapping("/findBook")
    @ResponseBody
    public List findBook(
            @RequestParam(defaultValue = "name",required = false) String searchField,
            @RequestParam(defaultValue = "Roman",required = false) String searchParam,
            @RequestParam(defaultValue = "name",required = false) String sortField,
            @RequestParam(defaultValue = "asc" ,required = false) String sortDir
            ) {
        return bookServiceImpl.findBook(searchField, searchParam, sortField, sortDir);
    }
}
