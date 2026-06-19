package com.marcos.cards_batch.batch.flatten;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.batch.writer.ColorIdentityWriter;
import com.marcos.cards_batch.domain.entity.ColorIdentityJdbc;

@Component
public class ColorIdentityFlatten implements ItemWriter<List<ColorIdentityJdbc>>{
    private final ColorIdentityWriter delegate;

    public ColorIdentityFlatten(ColorIdentityWriter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(Chunk<? extends List<ColorIdentityJdbc>> chunk) throws Exception {
        List<ColorIdentityJdbc> flat = chunk.getItems()
            .stream()
            .flatMap(items -> items.stream())
            .toList();
        
        delegate.write(new Chunk<>(flat));
    }
}
