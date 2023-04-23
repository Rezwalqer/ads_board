package ru.skypro.avito.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateAds {
    @NotBlank
    @Size(min = 8)
    private String description;
    private Integer price;
    @NotBlank
    @Size(min = 8)
    private String title;
}
