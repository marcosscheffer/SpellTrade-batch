package com.marcos.cards_batch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record CardFacesDto(
    String name,

    @JsonProperty("mana_cost")
    String manaCost,

    @JsonProperty("type_line")
    String typeLine,

    @JsonProperty("oracle_text")
    String oracleText,

    String power,
    String toughness,
    String loyalty,

    @JsonProperty("image_uris")
    ImageDto imageUris
) {
}
