package com.example.magic.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Language {
    @JsonProperty("português")
    PORTUGUES,
    @JsonProperty("inglês")
    INGLES,
    @JsonProperty("japones")
    JAPONES;
}
