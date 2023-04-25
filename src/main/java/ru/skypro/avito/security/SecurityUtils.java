package ru.skypro.avito.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.avito.model.Ads;
import ru.skypro.avito.model.Comment;

@UtilityClass
public class SecurityUtils {

    public static MyUserDetails getUserDetailsFromContext() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Integer getUserIdFromContext() {
        return getUserDetailsFromContext().getUser().getId();
    }

    public static void checkPermissionToAds(Ads ads) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getUser().getId().equals(ads.getAuthor().getId())) {
            throw new AccessDeniedException("Чтобы изменить/удалить объявление, нужно иметь роль ADMIN или быть владельцем этого объявления");
        }
    }

    public static void checkPermissionToAdsComment(Comment comment) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getUser().getId().equals(comment.getAuthor().getId())) {
            throw new AccessDeniedException("Чтобы изменить/удалить комментарий, нужно иметь роль ADMIN или быть владельцем этого комментария");
        }
    }
}
