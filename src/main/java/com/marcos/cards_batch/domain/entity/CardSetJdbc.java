package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import com.marcos.cards_batch.domain.enums.SetType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardSetJdbc {
    private UUID id;
    private String name;
    private String code;
    private SetType type;
}
