package com.marcos.cards_batch.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ScryfallCardDto(
    // Sets
    @JsonProperty("set_id")
    UUID setId,
    @JsonProperty("set_name")
    String setName,
    String set,
    @JsonProperty("set_type")
    String setType,

    // Cards
    UUID id,

    @JsonProperty("oracle_id")
    UUID oracleId,
    String name,
    String lang,

    @JsonProperty("mana_cost")
    String manaCost,

    @JsonProperty("released_at")
    LocalDate releasedAt,

    @JsonProperty("type_line")
    String typeLine,

    @JsonProperty("oracle_text")
    String oracleText,

    @JsonProperty("color_identity")
    List<String> colorIdentity,
    boolean reserved,
    String power,
    String toughness,
    String loyalty,
    String rarity,

    // Card Faces
    @JsonProperty("card_faces")
    List<CardFacesDto> cardFaces,

    // Images
    @JsonProperty("image_uris")
    ImageDto imageUris,

    // Legatilies
    Map<String, String> legalities
) {
}
