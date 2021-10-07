package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired BookRepository repository;

    @PostMapping("/addBook")
    ResponseEntity<Void> addBook(@RequestBody Book book){
        repository.save(book);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books")
    List<Book> getBooks(){
        return repository.findAll();
    }

    @PutMapping("/updateBook")
    ResponseEntity<Object> updateBook(@RequestBody Book newBook,@RequestParam Long id){
        return repository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setAuthor(newBook.getAuthor());
                    book.setIsbnCode(newBook.getIsbnCode());
                    book.setYearOfPublishing(newBook.getYearOfPublishing());
                    repository.save(book);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/deleteBook")
    ResponseEntity<Void> deleteBook(@RequestParam Long id){
        try{
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (EmptyResultDataAccessException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findBook")
    @ResponseBody
    public List<Book> findBook(
            @RequestParam() String searchField,
            @RequestParam() String searchParam,
            @RequestParam(defaultValue = "name",required = false) String sortField,
            @RequestParam(defaultValue = "ASC" ,required = false) String sortDir
            ) {
        List<Book> bookList = new ArrayList<>();
        switch (searchField){
            case "name":
                bookList =  repository.findByNameAndSort(searchParam,Sort.by(Sort.Direction.fromString(sortDir),sortField));
                break;
            case "author":
                bookList = repository.findByAuthorAndSort(searchParam,Sort.by(Sort.Direction.fromString(sortDir),sortField));
                break;
            case "yearOfPublishing":
                bookList = repository.findByYearOfPublishingAndSort(searchParam,Sort.by(Sort.Direction.fromString(sortDir),sortField));
                break;
            case "ISBNCode":
                bookList = repository.findByISBNCodeAndSort(searchParam,Sort.by(Sort.Direction.fromString(sortDir),sortField));
                break;
        }
        return bookList;
    }
}
