package ru.skypro.avito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.avito.model.User;
import ru.skypro.avito.repository.UserRepository;
import ru.skypro.avito.service.AuthService;

import javax.validation.ValidationException;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    public void login(String username, String password) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        log.info("User with username {} was authenticated", username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password!");
        }
    }

    @Override
    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("User " + user.getEmail() + " already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
