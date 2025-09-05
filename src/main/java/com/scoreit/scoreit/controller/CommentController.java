package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.comment.CommentResponse;
import com.scoreit.scoreit.entity.Comment;
import com.scoreit.scoreit.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(
            @RequestParam Long memberId,
            @RequestParam Long reviewId,
            @RequestParam String content,
            @RequestParam(required = false) Long parentId
    ) {
        Comment comment = commentService.createComment(memberId, reviewId, content, parentId);
        return ResponseEntity.ok(
                new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getAuthor().getId(),
                        comment.getAuthor().getName(),
                        comment.getCreatedAt(),
                        List.of()
                )
        );
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.getCommentsByReview(reviewId));
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long memberId
    ) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.ok("Comentário deletado com sucesso");
    }

}
