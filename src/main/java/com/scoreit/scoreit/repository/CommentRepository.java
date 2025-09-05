package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Comment;
import com.scoreit.scoreit.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewAndParentIsNullOrderByCreatedAtDesc(Review review);
}