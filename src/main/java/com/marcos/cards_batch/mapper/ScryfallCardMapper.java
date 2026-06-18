package com.marcos.cards_batch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.marcos.cards_batch.domain.entity.CardJdbc;
import com.marcos.cards_batch.domain.enums.RarityType;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Mapper(componentModel = "spring")
public interface ScryfallCardMapper {
    @Mapping(source = "rarity", target = "rarity", qualifiedByName = "mapRarity")
    CardJdbc toEntity(ScryfallCardDto dto);
    @Named("mapRarity")
    default RarityType mapRarity(String rarity) {
        return RarityType.valueOf(rarity.toUpperCase());
    }
}
