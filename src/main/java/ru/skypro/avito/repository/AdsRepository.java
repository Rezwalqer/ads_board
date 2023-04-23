package ru.skypro.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.avito.model.Ads;

import java.util.Collection;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    Collection<Ads> findAllByAuthorId(Integer id);
}
