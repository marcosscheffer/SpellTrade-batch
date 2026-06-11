package com.marcos.cards_batch.domain.entity;

import com.marcos.cards_batch.domain.entity.enums.LegalityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "card_legalities")
@Setter
@Getter
public class CardLegality {
    @EmbeddedId
    private CardLegalityId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cardId")
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(columnDefinition = "legality_status")
    private LegalityStatus status;
}
