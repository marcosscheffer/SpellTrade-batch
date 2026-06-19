package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.ColorIdentityJdbc;
import com.marcos.cards_batch.domain.enums.Color;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Component
public class ColorIdentityProcessor implements ItemProcessor<ScryfallCardDto, List<ColorIdentityJdbc>> {
    @Override
    public @Nullable List<ColorIdentityJdbc> process(ScryfallCardDto item) throws Exception {
        List<ColorIdentityJdbc> colorIdentities = new ArrayList<>();

        for (String color : item.colorIdentity()) {
            ColorIdentityJdbc colorIdentity = new ColorIdentityJdbc();
            colorIdentity.setCardId(item.id());
            colorIdentity.setColor(Color.valueOf(color.toUpperCase()));
            colorIdentities.add(colorIdentity);
        }

        return colorIdentities;
    }
}
