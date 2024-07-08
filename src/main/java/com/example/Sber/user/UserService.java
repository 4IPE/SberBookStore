package com.example.Sber.user;

import java.util.List;

public interface UserService {
    User addUser(User user);

    List<User> getAllUsers();

    void delUser(Long id);

    void userUpdRole(Long userid, Long roleId);

    User getCurrentUser();
}
