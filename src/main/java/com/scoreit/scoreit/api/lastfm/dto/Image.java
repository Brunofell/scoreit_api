package com.scoreit.scoreit.api.lastfm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Image(@JsonProperty("#text") String imageUrl) {}