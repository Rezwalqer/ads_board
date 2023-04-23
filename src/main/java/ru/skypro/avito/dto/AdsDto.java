package ru.skypro.avito.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdsDto {
    private Integer pk;
    private Integer author;
    private String image;
    private Integer price;
    @NotBlank
    @Size(min = 8)
    private String title;
    @NotBlank
    @Size(min = 8)
    private String description;
}
