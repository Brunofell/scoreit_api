package com.scoreit.scoreit.api.music.lastfm.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Image(@JsonProperty("#text") String imageUrl) {}