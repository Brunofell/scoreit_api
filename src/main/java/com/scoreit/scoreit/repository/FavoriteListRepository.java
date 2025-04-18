package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {
    FavoriteList findByMemberIdAndListName(Long memberId, String favorites);

    Optional<FavoriteList> findByMemberId(Long memberId);
}
