package com.scoreit.scoreit.api.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Image(@JsonProperty("#text") String imageUrl) {}