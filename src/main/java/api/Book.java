package api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Year;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String name;
    String author;
    Year yearOfPublishing;
    long isbnCode;

    public Book(){}

    Book (String name, String author, Year yearOfPublishing, long isbnCode) {
        this.name = name;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
        this.isbnCode = isbnCode;
    }


    public long getId(){
        return id;
   }

    public String getName () {
        return name;
    }

    public String getAuthor () {
        return author;
    }

    public Year getYearOfPublishing () {
        return yearOfPublishing;
    }

    public long getIsbnCode () {
        return isbnCode;
    }

    public void setAuthor (String author) {
        this.author = author;
    }

    public void setIsbnCode (long isbnCode) {
        this.isbnCode = isbnCode;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setYearOfPublishing (Year yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbnCode == book.isbnCode;
    }

    @Override
    public int hashCode () {
        return Objects.hash(isbnCode);
    }

    @Override
    public String toString () {
        return "Name: " + this.name +
                " Author: " + this.author +
                " Year of publishing: " + this.yearOfPublishing +
                " ISBN code: " + this.isbnCode;
    }
}
