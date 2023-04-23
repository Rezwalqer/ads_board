package ru.skypro.avito.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.avito.model.User;
import ru.skypro.avito.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new MyUserDetails(user);
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = getUserByUsername(userDetails.getUsername());

        user.setPassword(newPassword);

        MyUserDetails updatedUserDetails = new MyUserDetails(userRepository.save(user));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(updatedUserDetails, null, updatedUserDetails.getAuthorities())
        );

        return updatedUserDetails;
    }

    private User getUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с email: " + username + " не найден"));
    }
}
