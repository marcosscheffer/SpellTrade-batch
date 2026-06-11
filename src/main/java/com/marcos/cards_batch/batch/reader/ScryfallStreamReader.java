package com.marcos.cards_batch.batch.reader;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Component
public class ScryfallStreamReader implements ItemReader<ScryfallCardDto>{

    @Override
    public @Nullable ScryfallCardDto read() throws Exception {
        return null;
    }
}