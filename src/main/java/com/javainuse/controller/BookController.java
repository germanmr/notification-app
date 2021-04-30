package com.javainuse.controller;

import com.javainuse.dto.Square;
import com.javainuse.entity.Book;
import com.javainuse.mapper.MapperImpl;
import com.javainuse.repository.BookDao;
import com.javainuse.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookDao bookDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    Environment env;

    @Autowired
    MapperImpl mapper;

    @GetMapping("/")
    public List<Book> get() {

//        System.out.println(verdadero);
//        Boolean value = verdadero;
//
//        Boolean logLevel = Boolean.parseBoolean(env.getProperty("com.german.myname"));

        String authorName = "Some";
        String title = "Some";

        long id = 1;
        bookDao.insertWithEntityManager(new Book(id, "Some", "Some"));

        List<Book> books = bookDao.findBooksByAuthorNameAndTitle(authorName, title);

        return books;
    }

    @GetMapping("/group")
    public void group() {
        bookDao.group();
    }

    @GetMapping("/employee")
    public void employee() {
        employeeDao.initiate();
        employeeDao.employee();
    }

    @GetMapping("/square")
    public Square getSquare() {
        try {
            Square square = new Square(null, -2, "blue");
            Square squareDTO = mapper.validateInputWithInjectedValidator(square);
            return squareDTO;
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(v -> v.getConstraintDescriptor());
            return null;
        }
    }
}
