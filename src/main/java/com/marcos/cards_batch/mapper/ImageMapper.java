package com.marcos.cards_batch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.marcos.cards_batch.domain.entity.ImageJdbc;
import com.marcos.cards_batch.dto.ImageDto;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "cardId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cardFace", ignore = true)
    @Mapping(target = "faceIndex", ignore = true)
    ImageJdbc toEntity(ImageDto dto);
}
