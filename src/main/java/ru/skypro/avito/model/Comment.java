package ru.skypro.avito.model;


import lombok.Data;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User author;
    private Instant createdAt;
    private String text;
    @ManyToOne
    private Ads ads;
}
