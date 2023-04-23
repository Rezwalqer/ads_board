package ru.skypro.avito.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewPassword {
    @NotBlank
    @Size(min = 8)
    private String currentPassword;
    @NotBlank
    @Size(min = 8)
    private String newPassword;
}
