package com.marcos.cards_batch.domain.entity;

import java.time.LocalDate;
import java.util.UUID;
import com.marcos.cards_batch.domain.enums.RarityType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card {
    @Id
    private UUID id;
    private UUID oracleId;
    private String name;
    private String lang;
    private String manaCost;
    private LocalDate releasedAt;
    private String typeLine;
    private String oracleText;

    private Boolean reserved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id")
    private CardSet set;

    private String power;
    private String toughness;
    private String loyalty;

    @Enumerated(EnumType.STRING)
    private RarityType rarity;
}
