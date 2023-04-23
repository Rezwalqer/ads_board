package ru.skypro.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.avito.dto.LoginReq;
import ru.skypro.avito.service.AuthService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Tag(name = "Авторизация")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginReq req) {
        authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok().build();
    }

}
