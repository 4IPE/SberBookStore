package com.example.Sber.author;

import com.example.Sber.Author.Author;
import com.example.Sber.Author.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestPropertySource(properties = {"db.name=test"})
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    private Author author;

    @BeforeEach
    void setUp() {
        this.author = createAuthor("Author");
    }

    private Author createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    @Test
    void findByNameTest() {
        Author findAuthor = authorRepository.findByName("Author");
        assertThat(findAuthor)
                .isNotNull()
                .isEqualTo(author);
    }
}
