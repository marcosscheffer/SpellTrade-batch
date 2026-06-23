package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import com.marcos.cards_batch.domain.enums.SetType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    schema = "card",
    name = "sets")
@Getter
@Setter
public class CardSet {
    @Id
    private UUID id;

    private String name;
    private String code;

    @Enumerated(EnumType.STRING)
    private SetType type;
}