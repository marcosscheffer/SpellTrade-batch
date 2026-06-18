package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import com.marcos.cards_batch.domain.enums.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorIdentityJdbc {
    private UUID cardId;
    private Color color;
}
