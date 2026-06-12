package com.marcos.cards_batch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageDto(
    String small,
    String normal,
    String large,
    String png,

    @JsonProperty("art_crop")
    String artCrop,

    @JsonProperty("border_crop")
    String borderCrop
) {

}
