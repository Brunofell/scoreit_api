package com.scoreit.scoreit.dto.customList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomListRegisterData(
        @NotNull(message = "memberId não pode ser nulo") Long memberId,
        @NotBlank(message = "listName não pode estar em branco") String listName,
        String description
) {}
