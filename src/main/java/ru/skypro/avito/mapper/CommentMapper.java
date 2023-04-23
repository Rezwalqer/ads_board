package ru.skypro.avito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.skypro.avito.model.Image;
import ru.skypro.avito.dto.CommentDto;
import ru.skypro.avito.model.Comment;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    String ADS_IMAGES = "/ads/images/";


    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", source = "author.image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "createdAt", source = "entity.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CommentDto toDto(Comment entity);

    @Named("imageMapping")
    default String avatarMapping(Image avatar) {
        if (avatar == null) {
            return null;
        }
        return ADS_IMAGES + avatar.getId();
    }
}