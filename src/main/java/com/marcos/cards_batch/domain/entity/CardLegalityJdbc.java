package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import com.marcos.cards_batch.domain.enums.Format;
import com.marcos.cards_batch.domain.enums.LegalityStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardLegalityJdbc {
    private UUID cardId;
    private Format format;
    private LegalityStatus status;
}
