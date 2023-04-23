package ru.skypro.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.avito.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
