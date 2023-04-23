package ru.skypro.avito.service;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.avito.model.Image;

public interface ImageService {
    /**
     * Сохранение картинки в БД
     */
    @SneakyThrows
    Image uploadImage(MultipartFile imageFile);
    /**
     * Получение картинки из БД
     */
    Image getImageById(Integer id);
}
