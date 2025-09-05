package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.comment.CommentResponse;
import com.scoreit.scoreit.entity.Comment;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.CommentRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    public CommentService(CommentRepository commentRepository,
                          MemberRepository memberRepository,
                          ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.reviewRepository = reviewRepository;
    }

    public Comment createComment(Long memberId, Long reviewId, String content, Long parentId) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setReview(review);
        comment.setContent(content);

        if (parentId != null) {
            Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParent(parent);
        }

        return commentRepository.save(comment);
    }

    public List<CommentResponse> getCommentsByReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        List<Comment> comments = commentRepository.findByReviewAndParentIsNullOrderByCreatedAtDesc(review);

        return comments.stream().map(this::toResponse).toList();
    }

    private CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.isDeleted() ? "[comentário deletado]" : comment.getContent(),
                comment.isDeleted() ? null : comment.getAuthor().getId(),
                comment.isDeleted() ? null : comment.getAuthor().getName(),
                comment.getCreatedAt(),
                comment.getReplies().stream().map(this::toResponse).toList()
        );
    }


    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));

        // Verifica se o usuário é o dono (ou você pode permitir admin também)
        if (!comment.getAuthor().getId().equals(memberId)) {
            throw new RuntimeException("Usuário não autorizado a deletar este comentário");
        }

        comment.setDeleted(true);
        comment.setContent("[comentário deletado]");

        commentRepository.save(comment);
    }
}
