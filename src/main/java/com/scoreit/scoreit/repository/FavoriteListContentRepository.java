package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.dto.favoriteList.TopMediaProjection;
import com.scoreit.scoreit.entity.FavoriteList;
import com.scoreit.scoreit.entity.FavoriteListContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteListContentRepository extends JpaRepository<FavoriteListContent, Long> {
    Optional<FavoriteListContent> findByFavoriteListAndMediaIdAndMediaType(FavoriteList favoriteList, String mediaId, String mediaType);

    Optional<FavoriteListContent> findByFavoriteListAndMediaId(FavoriteList favoriteList, String mediaId);

    List<FavoriteListContent> findByFavoriteList(FavoriteList favoriteList);

    @Query(value = """
            SELECT f.media_id AS mediaId, COUNT(*) AS total
            FROM favorite_list_content f
            WHERE f.media_type = :mediaType
            GROUP BY f.media_id
            ORDER BY total DESC
            LIMIT :limit
    """, nativeQuery = true)
    List<TopMediaProjection> findFavoriteMedia(@Param("mediaType") String mediaType, @Param("limit") int limit);
}
