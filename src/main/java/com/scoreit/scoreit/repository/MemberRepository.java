package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    Member findByHandle(String handle);

    Optional<Member> findOptionalByEmail(String email);

    List<Member> findByHandleContainingIgnoreCase(String handle);

    List<Member> findByEnabled(boolean enabled);

    @Query("SELECT m FROM Member m WHERE m.createdAt BETWEEN :start AND :end")
    List<Member> findMembersByCreationDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // Filtro de busca por nome, email ou handle
    @Query("""
        SELECT m FROM Member m
        WHERE (:query IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(m.email) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(m.handle) LIKE LOWER(CONCAT('%', :query, '%')))
        """)
    Page<Member> searchMembers(String query, Pageable pageable);

    // Crescimento de usuários por mês
    @Query("SELECT FUNCTION('DATE_FORMAT', m.createdAt, '%Y-%m') AS month, COUNT(m) AS total " +
            "FROM Member m " +
            "GROUP BY FUNCTION('DATE_FORMAT', m.createdAt, '%Y-%m') " +
            "ORDER BY month ASC")
    List<Object[]> countMembersByMonth();



}
