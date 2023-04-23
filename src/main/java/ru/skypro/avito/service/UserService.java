package ru.skypro.avito.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.avito.model.User;
import ru.skypro.avito.dto.UserDto;

public interface UserService {
    UserDto updateUser(UserDto updatedUser);
    UserDto getUser();
    void updatePassword(String newPassword, String currentPassword);
    void updateAvatar(MultipartFile file);
    User findUserFromContext();
}
