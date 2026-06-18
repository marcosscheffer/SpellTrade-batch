package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardFaceJdbc {
    private UUID cardId;
    private Short faceIndex;
    private String name;
    private String manaCost;
    private String typeLine;
    private String oracleText;
    private String power;
    private String toughness;
    private String loyalty;
}
