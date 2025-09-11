package com.scoreit.scoreit.dto.review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaType {
    MOVIE,
    SERIES,
    ALBUM;

    @JsonCreator
    public static MediaType fromJson(String value) {
        if (value == null) return null;
        return MediaType.valueOf(value.trim().toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }

    /** Converte para a string esperada pelo front (minúscula). */
    public String toFrontend() {
        return this.name().toLowerCase();
    }
}
