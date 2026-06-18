package com.marcos.cards_batch.batch.flatten;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.batch.writer.CardFaceWriter;
import com.marcos.cards_batch.domain.entity.CardFaceJdbc;

@Component
public class CardFaceFlatten implements ItemWriter<List<CardFaceJdbc>>{
    private final CardFaceWriter delegate;

    public CardFaceFlatten(CardFaceWriter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(Chunk<? extends List<CardFaceJdbc>> chunk) throws Exception {
        List<CardFaceJdbc> flat = chunk.getItems()
            .stream()
            .flatMap(items -> items.stream())
            .toList();
        
        delegate.write(new Chunk<>(flat));
    }


    
}
