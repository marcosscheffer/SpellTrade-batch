package com.marcos.cards_batch.mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardLegality;
import com.marcos.cards_batch.domain.entity.CardLegalityId;
import com.marcos.cards_batch.domain.enums.Format;
import com.marcos.cards_batch.domain.enums.LegalityStatus;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Mapper(componentModel = "spring")
public interface LegalitiesMapper {
    @Mapping(target = "id", ignore = true)
    CardLegality toEntity(ScryfallCardDto dto);

    default List<CardLegality> mapLegality(Map<String, String> legalities, Card card) {
        return legalities.entrySet().stream()
            .map(entry -> {
                Format format;
                LegalityStatus status;
                try {
                    format = Format.valueOf(entry.getKey().toUpperCase());
                } catch (Exception e) {
                    return null; // ignora formato desconhecido
                }

                try {
                    status = LegalityStatus.valueOf(entry.getValue().toUpperCase());
                } catch (Exception e) {
                    return null;
                }

                CardLegality cardLegality = new CardLegality();
                CardLegalityId id = new CardLegalityId();
                id.setFormat(format);
                id.setCardId(card.getId());
                
                cardLegality.setStatus(status);
                cardLegality.setId(id);

                return cardLegality;
            })
            .filter(Objects::nonNull)
            .toList();
    }
}
