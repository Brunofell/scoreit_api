package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.CustomList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomListRepository extends JpaRepository<CustomList, Long> {
    List<CustomList> findByMemberId(Long memberId);


    CustomList findByMemberIdAndListName(Long memberId, String favorites);

    Optional<CustomList> findByIdAndMemberId(Long id, Long memberId);

}
