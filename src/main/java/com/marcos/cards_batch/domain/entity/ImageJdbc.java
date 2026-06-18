package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageJdbc {
    private Long id;
    private Long cardFace;
    private UUID cardId;
    private short faceIndex;
    private String small;
    private String normal;
    private String large;
    private String png;
    private String artCrop;
    private String borderCrop;
}
