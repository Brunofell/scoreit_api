package com.scoreit.scoreit.dto.graph;

import java.time.LocalDate;

public record ReviewsByDateDTO(LocalDate date, Long total) {}