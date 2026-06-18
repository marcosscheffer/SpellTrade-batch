package com.marcos.cards_batch.batch.flatten;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.batch.writer.ImageWriter;
import com.marcos.cards_batch.domain.entity.ImageJdbc;

@Component
public class ImageFlatten implements ItemWriter<List<ImageJdbc>> {
    private final ImageWriter delegate;

    public ImageFlatten(ImageWriter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(Chunk<? extends List<ImageJdbc>> chunk) throws Exception {
        List<ImageJdbc> flat = chunk.getItems()
            .stream()
            .flatMap(items -> items.stream())
            .toList();
        
        delegate.write(new Chunk<>(flat));
    }
}
