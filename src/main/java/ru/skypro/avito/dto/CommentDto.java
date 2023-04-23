package ru.skypro.avito.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private Integer pk;
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private String createdAt;
    @NotBlank
    @Size(min = 8)
    private String text;
}
