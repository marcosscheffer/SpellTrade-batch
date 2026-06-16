package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import com.marcos.cards_batch.domain.enums.Color;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ColorIdentityId {
    private UUID cardId;
    
    @Enumerated(EnumType.STRING)
    private Color color;
}
