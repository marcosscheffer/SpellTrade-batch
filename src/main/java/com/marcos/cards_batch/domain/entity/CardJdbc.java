package com.marcos.cards_batch.domain.entity;

import java.time.LocalDate;
import java.util.UUID;
import com.marcos.cards_batch.domain.enums.RarityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardJdbc {
    private UUID id;
    private UUID oracleId;
    private String name;
    private String lang;
    private String manaCost;
    private LocalDate releasedAt;
    private String typeLine;
    private String oracleText;
    private Boolean reserved;
    private UUID setId;
    private String power;
    private String toughness;
    private String loyalty;
    private RarityType rarity;
}
