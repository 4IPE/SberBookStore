package com.example.Sber.book;

import com.example.Sber.Author.Author;
import com.example.Sber.Author.AuthorRepository;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(path = "/book")
    public String getBookDetails(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("book", bookService.getBookWithId(id));
        return "book-details";
    }

    @GetMapping("/employee/add-book")
    public String showFormAddBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("languages", Language.values());
        model.addAttribute("genres", Genre.values());
        List<Author> authors = authorRepository.findAll() == null ? new ArrayList<>() : authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "add-book";
    }

    @PostMapping("/employee/add-book")
    public String addBook(@ModelAttribute BookInDto book, @RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes) {
        String imagePath = "default.jpg";
        if (!file.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString() + "." + file.getOriginalFilename().split("\\.")[1];
                Path filepath = Paths.get(uploadPath, filename);
                Files.createDirectories(filepath.getParent());
                Files.write(filepath, file.getBytes());
                imagePath = filename;
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Could not upload file!");
                return "redirect:/employee/add-book";
            }
        }
        bookService.addBook(book, imagePath);
        return "redirect:/";
    }

    @GetMapping("/employee/add-author")
    public String showFormAddAuthor(Model model) {
        return "add-author";
    }

    @PostMapping("/employee/add-author")
    public String addAuthor(@ModelAttribute Author author) {
        authorRepository.save(author);
        return "redirect:/employee/add-book";
    }

    @PostMapping("/admin/book-remove")
    public String delete(@RequestParam(name = "bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/books")
    public String getBookDetails(Model model) {
        model.addAttribute("books", bookService.allBooks());
        return "booksData";
    }

    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Author.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Author author = authorRepository.findById(Long.valueOf(text)).orElse(null);
                setValue(author);
            }
        });
    }


}
