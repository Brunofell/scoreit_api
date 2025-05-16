package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.CustomList;
import com.scoreit.scoreit.entity.CustomListContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomListContentRepository extends JpaRepository<CustomListContent, Long> {
    Optional<CustomListContent> findByCustomListAndMediaIdAndMediaType(CustomList customList, String mediaId, String mediaType);

    List<CustomListContent> findByCustomList(CustomList customList);

    Optional<CustomListContent> findByCustomListAndMediaId(CustomList customList, String mediaId);
}
