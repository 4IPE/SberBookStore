package com.example.Sber.sec;

import com.example.Sber.Author.Author;
import com.example.Sber.Author.AuthorRepository;
import com.example.Sber.book.Book;
import com.example.Sber.book.BookRepository;
import com.example.Sber.user.Role;
import com.example.Sber.user.RoleRepository;
import com.example.Sber.user.User;
import com.example.Sber.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_EMPLOYER");
        createUserIfNotFound("dan", "12345", "ROLE_USER");
        createUserIfNotFound("emp", "12345", "ROLE_EMPLOYER");
        createUserIfNotFound("admin", "12345", "ROLE_ADMIN");
        Author author = createAuthorIfNotFound("author1");

    }

    private void createRoleIfNotFound(String roleName) {
        if (roleRepository.findByName(roleName) == null) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    private void createUserIfNotFound(String userName, String password, String role) {
        if (userRepository.findByUsername(userName) == null) {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roleRepository.findByName(role));
            userRepository.save(user);
        }
    }

    private void createBookIfNotFound(String name, Author author, Date date) {
        if (bookRepository.findByName(name) == null) {
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setDate(date);
            bookRepository.save(book);
        }
    }

    private Author createAuthorIfNotFound(String authorName) {
        Author authorFind = authorRepository.findByName(authorName);
        if (authorFind == null) {
            Author author = new Author();
            author.setName(authorName);
            return authorRepository.save(author);
        }
        return authorFind;
    }
}

