package com.marcos.cards_batch.domain.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.marcos.cards_batch.domain.entity.enums.Color;
import com.marcos.cards_batch.domain.entity.enums.RarityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "color_identity", columnDefinition = "color[]")
    private List<Color> colorIdentity;

    private Boolean reserved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id")
    private CardSet set;

    private String power;
    private String toughness;
    private String loyalty;

    @Column(columnDefinition = "rarity_type")
    private RarityType rarity;
}
