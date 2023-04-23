package ru.skypro.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.avito.model.Comment;

import java.util.Collection;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Collection<Comment> findAllByAdsId(Integer adsId);

    Optional<Comment> findByIdAndAdsId(Integer id, Integer adsId);

}
