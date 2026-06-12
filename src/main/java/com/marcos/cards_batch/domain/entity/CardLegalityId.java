package com.marcos.cards_batch.domain.entity;

import java.io.Serializable;
import java.util.UUID;
import com.marcos.cards_batch.domain.enums.Format;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
public class CardLegalityId implements Serializable{
    private UUID cardId;

    @Enumerated(EnumType.STRING)
    private Format format;
}
