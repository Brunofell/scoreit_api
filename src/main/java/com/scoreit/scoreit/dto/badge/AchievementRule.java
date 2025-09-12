package com.scoreit.scoreit.dto.badge;

public enum AchievementRule {
    // FILMES
    MOVIE_FIRST("MOVIE_FIRST", "MOVIE", 1),
    MOVIE_10("MOVIE_10", "MOVIE", 10),
    MOVIE_50("MOVIE_50", "MOVIE", 50),
    MOVIE_100("MOVIE_100", "MOVIE", 100),

    // SÉRIES
    SERIES_FIRST("SERIES_FIRST", "SERIES", 1),
    SERIES_10("SERIES_10", "SERIES", 10),
    SERIES_30("SERIES_30", "SERIES", 30),
    SERIES_50("SERIES_50", "SERIES", 50),

    // ÁLBUNS (corrigido)
    ALBUM_FIRST("ALBUM_FIRST", "ALBUM", 1),
    ALBUM_10("ALBUM_10", "ALBUM", 10),
    ALBUM_50("ALBUM_50", "ALBUM", 50),
    ALBUM_100("ALBUM_100", "ALBUM", 100);

    private final String code;
    private final String type;
    private final int threshold;

    AchievementRule(String code, String type, int threshold) {
        this.code = code;
        this.type = type;
        this.threshold = threshold;
    }

    public String getCode() { return code; }
    /** "MOVIE" | "SERIES" | "ALBUM" */
    public String getType() { return type; }
    public int getThreshold() { return threshold; }
}
