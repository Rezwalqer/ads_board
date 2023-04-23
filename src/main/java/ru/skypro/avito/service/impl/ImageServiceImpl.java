package ru.skypro.avito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.avito.model.Image;
import ru.skypro.avito.repository.ImageRepository;
import ru.skypro.avito.service.ImageService;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @SneakyThrows
    public Image uploadImage(MultipartFile imageFile){
        Image image = new Image();
        image.setImage(imageFile.getBytes());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setImage(imageFile.getBytes());
        log.info("Image is uploaded");
        return imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    @Override
    public Image getImageById(Integer id) {

        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
    }

}
