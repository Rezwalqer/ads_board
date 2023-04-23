package ru.skypro.avito.service;

import ru.skypro.avito.model.User;

public interface AuthService {
    void login(String username, String password);
    void register(User user);
}
