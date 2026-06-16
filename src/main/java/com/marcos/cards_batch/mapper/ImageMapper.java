package com.marcos.cards_batch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.marcos.cards_batch.domain.entity.Image;
import com.marcos.cards_batch.dto.ImageDto;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cardFace", ignore = true)
    @Mapping(target = "faceIndex", ignore = true)
    Image toEntity(ImageDto dto);
}
