package ru.skypro.avito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.skypro.avito.dto.CreateAds;
import ru.skypro.avito.model.Ads;
import ru.skypro.avito.dto.AdsDto;
import ru.skypro.avito.dto.FullAdsDto;

@Mapper
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    String ADS_IMAGES = "/ads/images/";

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", source = "image.id", qualifiedByName = "imageMapping")
    AdsDto toDto(Ads entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(CreateAds dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "image", source = "image.id", qualifiedByName = "imageMapping")
    FullAdsDto toFullAdsDto(Ads entity);

    @Named("imageMapping")
    default String imageMapping(Long value) {
        return ADS_IMAGES + value;
    }
}