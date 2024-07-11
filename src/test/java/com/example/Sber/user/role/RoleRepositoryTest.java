package com.example.Sber.user.role;


import com.example.Sber.user.Role;
import com.example.Sber.user.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"db.name=test"})
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    private Role role;

    @BeforeEach
    void setUp() {
        this.role = createRole("ROLE");
    }

    private Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Test
    void findByNameTest() {
        Role findRole = roleRepository.findByName("ROLE");
        assertThat(role).isNotNull();
        assertThat(role.getName()).isEqualTo("ROLE");
    }
}

