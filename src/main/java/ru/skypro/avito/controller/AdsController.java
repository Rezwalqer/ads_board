package ru.skypro.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.avito.dto.AdsDto;
import ru.skypro.avito.dto.CreateAds;
import ru.skypro.avito.dto.FullAdsDto;
import ru.skypro.avito.dto.ResponseWrapper;
import ru.skypro.avito.service.AdsService;
import ru.skypro.avito.service.ImageService;


@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    private final AdsService adsService;
    private final ImageService imageService;

    @Operation(summary = "Получить все объявления")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        return ResponseWrapper.of(adsService.getAllAds());
    }

    @SneakyThrows
    @Operation(summary = "Добавить объявление")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "Данные нового объявления")
                                         @RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") CreateAds dto) {
        return ResponseEntity.ok(adsService.createAds(image, dto));
    }

    @Operation(summary = "Получить объявления авторизованного пользователя")
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe() {
        return ResponseWrapper.of(adsService.getAdsMe());
    }

    @Operation(summary = "Удалить объявление")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable("id") Integer id) {
        adsService.removeAdsById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить информацию об объявлении")
    @GetMapping("/{id}")
    public FullAdsDto getAds(@PathVariable("id") Integer id) {
        return adsService.getAdsById(id);
    }

    @SneakyThrows
    @Operation(summary = "Обновить картинку объявления")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> updateAdsImage(@PathVariable("id") Integer id,
                                                 @Parameter(description = "Новая картинка")
                                                 @RequestPart(value = "image") MultipartFile image) {
        return ResponseEntity.ok(adsService.updateAdsImage(adsService.findAdsById(id), imageService.uploadImage(image)));
    }

    @SneakyThrows
    @Operation(summary = "Обновить информацию об объявлении")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable("id") Integer id,
                                            @RequestBody CreateAds updatedAdsDto) {
        return ResponseEntity.ok(adsService.updateAds(updatedAdsDto, id));
    }

    @GetMapping(value = "images/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(imageService.getImageById(id).getImage());
    }

}