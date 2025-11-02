package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.comment.CommentResponse;
import com.scoreit.scoreit.entity.Comment;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.CommentRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private CommentService commentService;

    private Member member;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member = new Member();
        member.setId(1L);
        member.setName("Bruno");

        review = new Review();
        review.setId(10L);
    }

    @Test
    void shouldCreateCommentSuccessfully() {
        // Arrange
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(reviewRepository.findById(10L)).thenReturn(Optional.of(review));

        Comment savedComment = new Comment();
        savedComment.setId(100L);
        savedComment.setAuthor(member);
        savedComment.setReview(review);
        savedComment.setContent("Hello");
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // Act
        Comment result = commentService.createComment(1L, 10L, "Hello", null);

        // Assert
        assertEquals(100L, result.getId());
        assertEquals(member, result.getAuthor());
        assertEquals(review, result.getReview());
        assertEquals("Hello", result.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void shouldCreateReplyToComment() {
        // Arrange
        Comment parent = new Comment();
        parent.setId(50L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(reviewRepository.findById(10L)).thenReturn(Optional.of(review));
        when(commentRepository.findById(50L)).thenReturn(Optional.of(parent));

        Comment savedComment = new Comment();
        savedComment.setId(101L);
        savedComment.setParent(parent);
        savedComment.setAuthor(member);
        savedComment.setReview(review);
        savedComment.setContent("Reply");

        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // Act
        Comment result = commentService.createComment(1L, 10L, "Reply", 50L);

        // Assert
        assertEquals(101L, result.getId());
        assertEquals(parent, result.getParent());
        assertEquals("Reply", result.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void shouldReturnCommentsByReview() {
        // Arrange
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setAuthor(member);
        comment.setReview(review);
        comment.setContent("Test");
        comment.setCreatedAt(LocalDateTime.now());

        when(reviewRepository.findById(10L)).thenReturn(Optional.of(review));
        when(commentRepository.findByReviewAndParentIsNullOrderByCreatedAtDesc(review))
                .thenReturn(List.of(comment));

        // Act
        List<CommentResponse> responses = commentService.getCommentsByReview(10L);

        // Assert
        assertEquals(1, responses.size());
        assertEquals("Test", responses.get(0).content());
    }

    @Test
    void shouldDeleteCommentSuccessfully() {
        // Arrange
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setAuthor(member);
        comment.setContent("Original");
        comment.setDeleted(false);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Act
        commentService.deleteComment(1L, 1L);

        // Assert
        assertTrue(comment.isDeleted());
        assertEquals("[comentário deletado]", comment.getContent());
        verify(commentRepository, times(1)).save(comment);
    }

//    @Test
//    void shouldThrowWhenDeletingCommentNotAuthor() {
//        // Arrange
//        Comment comment = new Comment();
//        comment.setId(1L);
//        comment.setAuthor(new Member()); // outro usuário
//        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
//
//        // Act & Assert
//        RuntimeException exception = assertThrows(RuntimeException.class,
//                () -> commentService.deleteComment(1L, 1L));
//        assertEquals("Usuário não autorizado a deletar este comentário", exception.getMessage());
//    }
}
