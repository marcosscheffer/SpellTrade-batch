package com.marcos.cards_batch.domain.entity;

import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.marcos.cards_batch.domain.entity.enums.Color;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "card_faces")
@Getter
@Setter
public class CardFace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card cardId;

    private Short faceIndex;
    private String name;
    private String manaCost;
    private String typeLine;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "color_identity", columnDefinition = "color[]")
    private List<Color> colorIdentity;

    private String oracleText;
}
