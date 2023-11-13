package de.workshops.bookshelf.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class BookViewController {
    private final BookService service;

    public BookViewController(BookService service) {
        this.service = service;
    }

    @GetMapping
    String viewAllBooks(Model model) {
        model.addAttribute("books", service.getAllBooks());
        return "allbooks";
    }
}
