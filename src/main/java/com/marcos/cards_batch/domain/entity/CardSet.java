package com.marcos.cards_batch.domain.entity;

import java.util.UUID;
import com.marcos.cards_batch.domain.entity.enums.SetType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sets")
@Getter
@Setter
public class CardSet {
    @Id
    private UUID id;

    private String name;
    private String code;

    @Column(columnDefinition = "set_type")
    private SetType type;
}
