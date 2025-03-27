package com.scoreit.scoreit.dto.music;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Image(@JsonProperty("#text") String imageUrl) {}