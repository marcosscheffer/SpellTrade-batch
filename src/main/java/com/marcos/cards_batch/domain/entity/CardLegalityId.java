package com.marcos.cards_batch.domain.entity;

import java.io.Serializable;
import java.util.UUID;
import com.marcos.cards_batch.domain.enums.Format;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
public class CardLegalityId implements Serializable{
    private UUID cardId;

    @Column(columnDefinition = "formats")
    private Format format;
}
