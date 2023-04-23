package ru.skypro.avito.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.avito.model.Image;
import ru.skypro.avito.dto.AdsDto;
import ru.skypro.avito.dto.CreateAds;
import ru.skypro.avito.dto.FullAdsDto;
import ru.skypro.avito.model.Ads;

import java.util.Collection;

public interface AdsService {
    AdsDto createAds(MultipartFile image, CreateAds dto);
    FullAdsDto getAdsById(Integer id);
    Collection<AdsDto> getAllAds();
    void removeAdsById(Integer id);
    AdsDto updateAds(CreateAds updatedAds, Integer adsId);
    Collection<AdsDto> getAdsMe();
    AdsDto updateAdsImage(Ads ads, Image image);
    Ads findAdsById(Integer id);
}
