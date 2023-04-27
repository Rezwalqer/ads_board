package ru.skypro.avito.security;

import lombok.Getter;
import ru.skypro.avito.model.User;

import java.util.List;

@Getter
public class MyUserDetails extends org.springframework.security.core.userdetails.User {

    private final User user;

    public MyUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.user = user;
    }

    @Override
    public void eraseCredentials() {
    }

}
