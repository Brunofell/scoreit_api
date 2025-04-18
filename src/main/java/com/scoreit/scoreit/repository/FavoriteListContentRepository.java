package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.FavoriteList;
import com.scoreit.scoreit.entity.FavoriteListContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteListContentRepository extends JpaRepository<FavoriteListContent, Long> {
    Optional<FavoriteListContent> findByFavoriteListAndMediaIdAndMediaType(FavoriteList favoriteList, String mediaId, String mediaType);

    List<FavoriteListContent> findByFavoriteList(FavoriteList favoriteList);
}
