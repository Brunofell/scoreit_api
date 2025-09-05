package com.scoreit.scoreit.dto.comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        Long id,
        String content,
        Long authorId,
        String authorName,
        LocalDateTime createdAt,
        List<CommentResponse> replies
) {}
