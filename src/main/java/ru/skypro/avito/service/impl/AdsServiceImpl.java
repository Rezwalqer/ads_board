package ru.skypro.avito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.avito.dto.AdsDto;
import ru.skypro.avito.dto.CreateAds;
import ru.skypro.avito.dto.FullAdsDto;
import ru.skypro.avito.mapper.AdsMapper;
import ru.skypro.avito.model.Ads;
import ru.skypro.avito.model.Image;
import ru.skypro.avito.model.User;
import ru.skypro.avito.repository.AdsRepository;
import ru.skypro.avito.service.AdsService;
import ru.skypro.avito.service.ImageService;
import ru.skypro.avito.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

import static ru.skypro.avito.security.SecurityUtils.checkPermissionToAds;
import static ru.skypro.avito.security.SecurityUtils.getUserIdFromContext;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class AdsServiceImpl implements AdsService {

    private final ImageService imagesService;
    private final UserService userService;
    private final AdsRepository adsRepository;

    @SneakyThrows
    @Override
    public AdsDto createAds(MultipartFile image, CreateAds dto) {
        Ads ads = AdsMapper.INSTANCE.toEntity(dto);
        User user = userService.findUserFromContext();
        ads.setImage(imagesService.uploadImage(image));
        ads.setAuthor(user);
        log.info("Ads with id {} is created", ads.getId());
        return AdsMapper.INSTANCE.toDto(adsRepository.save(ads));
    }

    @Transactional(readOnly = true)
    @Override
    public FullAdsDto getAdsById(Integer id) {
        log.info("Ads with id {} is founded", id);
        return AdsMapper.INSTANCE.toFullAdsDto(findAdsById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<AdsDto> getAllAds() {
        log.info("All ads is founded");
        return adsRepository.findAll()
                .stream()
                .map(AdsMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAdsById(Integer id) {
        Ads ads = findAdsById(id);
        checkPermissionToAds(ads);
        adsRepository.delete(ads);
        log.info("Ads with id {} is removed", id);
    }

    @Override
    public AdsDto updateAds(CreateAds createAds, Integer adsId) {
        Ads ads = findAdsById(adsId);
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        adsRepository.save(ads);
        log.info("Ads with id {} is updated", adsId);
        return AdsMapper.INSTANCE.toDto(ads);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<AdsDto> getAdsMe() {
        log.info("My ads is founded");
        return adsRepository.findAllByAuthorId(getUserIdFromContext()).stream()
                .map(AdsMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public AdsDto updateAdsImage(Ads ads, Image image) {
        checkPermissionToAds(ads);
        ads.setImage(image);
        log.info("Ads with id {} image is updated", ads.getId());
        return AdsMapper.INSTANCE.toDto(adsRepository.save(ads));
    }

    public Ads findAdsById(Integer id) {
        return adsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ads from id " + id + " not found"));
    }

}
