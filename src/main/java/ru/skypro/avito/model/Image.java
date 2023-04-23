package ru.skypro.avito.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Type(type = "binary")
    private byte[] image;
    private Long fileSize;
    private String mediaType;
}
