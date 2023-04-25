package ru.skypro.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.avito.dto.RegisterReq;
import ru.skypro.avito.mapper.UserMapper;
import ru.skypro.avito.service.AuthService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Tag(name = "Регистрация")
public class RegisterController {

    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody RegisterReq req) {
        authService.register(UserMapper.INSTANCE.toEntity(req));
        return ResponseEntity.ok().build();
    }
}
