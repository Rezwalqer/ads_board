package ru.skypro.avito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.skypro.avito.model.Image;
import ru.skypro.avito.model.User;
import ru.skypro.avito.dto.RegisterReq;
import ru.skypro.avito.dto.UserDto;

@Mapper
public interface UserMapper {


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    String ADS_IMAGES = "/ads/images/";

    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    UserDto entityToUserDto(User entity);


    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(source = "username", target = "email")
    User toEntity(RegisterReq dto);

    @Named("imageMapping")
    default String imageMapping(Image value) {
        if (value == null) {
            return null;
        }
        return ADS_IMAGES + value.getId();
    }

}