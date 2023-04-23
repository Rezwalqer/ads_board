package ru.skypro.avito.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.avito.dto.NewPassword;
import ru.skypro.avito.dto.UserDto;
import ru.skypro.avito.service.ImageService;
import ru.skypro.avito.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @Operation(summary = "Обновление пароля")
    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPassword) {
        userService.updatePassword(newPassword.getNewPassword(), newPassword.getCurrentPassword());

        return ResponseEntity.ok(newPassword);
    }

    @Operation(summary = "Получить информацию об авторизованном пользователе")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @Operation(summary = "Обновить информацию об авторизованном пользователе")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @SneakyThrows
    @Operation(summary = "Обновить аватар авторизованного пользователя")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@Parameter(description = "Новая картинка")
                                             @RequestPart(value = "image")  MultipartFile image) {
        userService.updateAvatar(image);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "images/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(imageService.getImageById(id).getImage());
    }
}
