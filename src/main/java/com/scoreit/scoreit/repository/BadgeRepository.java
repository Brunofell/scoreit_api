package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByCode(String code);
}
