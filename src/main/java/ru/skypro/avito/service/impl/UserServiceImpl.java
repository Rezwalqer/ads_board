package ru.skypro.avito.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.avito.model.User;
import ru.skypro.avito.dto.UserDto;
import ru.skypro.avito.repository.UserRepository;
import ru.skypro.avito.security.UserDetailsServiceImpl;
import ru.skypro.avito.service.ImageService;
import ru.skypro.avito.service.UserService;

import static ru.skypro.avito.mapper.UserMapper.INSTANCE;
import static ru.skypro.avito.security.SecurityUtils.getUserDetailsFromContext;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto updateUser(UserDto updatedUser) {
        User user = findUserFromContext();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());
        return INSTANCE.entityToUserDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser() {
        return INSTANCE.entityToUserDto(findUserFromContext());
    }

    @Override
    public void updatePassword(String newPassword, String currentPassword) {
        UserDetails userDetails = getUserDetailsFromContext();
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("The current password is incorrect");
        }
        userDetailsService.updatePassword(userDetails, passwordEncoder.encode(newPassword));
    }

    @Override
    public void updateAvatar(MultipartFile file) {
        User user = findUserFromContext();
        user.setImage(imageService.uploadImage(file));
        userRepository.save(user);
    }

    @Override
    public User findUserFromContext() {
        return getUserDetailsFromContext().getUser();
    }

}
