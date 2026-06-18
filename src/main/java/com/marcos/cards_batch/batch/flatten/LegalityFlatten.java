package com.marcos.cards_batch.batch.flatten;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.batch.writer.LegalityWriter;
import com.marcos.cards_batch.domain.entity.CardLegalityJdbc;

@Component
public class LegalityFlatten implements ItemWriter<List<CardLegalityJdbc>> {
    private final LegalityWriter delegate;

    public LegalityFlatten(LegalityWriter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(Chunk<? extends List<CardLegalityJdbc>> chunk) throws Exception {
        List<CardLegalityJdbc> flat = chunk.getItems()
            .stream()
            .flatMap(items -> items.stream())
            .toList();
        
        delegate.write(new Chunk<>(flat));
    }
}
