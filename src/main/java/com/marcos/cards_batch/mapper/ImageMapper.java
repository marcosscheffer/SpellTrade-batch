package com.marcos.cards_batch.mapper;

import org.mapstruct.Mapper;
import com.marcos.cards_batch.domain.entity.Image;
import com.marcos.cards_batch.dto.ImageDto;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toEntity(ImageDto dto);
}
