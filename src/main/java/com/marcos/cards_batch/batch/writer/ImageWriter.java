package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Image;
import com.marcos.cards_batch.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ImageWriter implements ItemWriter<List<Image>>{
    private final ImageRepository imageRepository;

    public ImageWriter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void write(Chunk<? extends List<Image>> chunk) throws Exception {
        List<? extends List<Image>> items = chunk.getItems();
        List<Image> images = items.stream()
            .flatMap(list -> list.stream())
            .toList();
            
        log.info("Saving Images - {} B", images.size());
        imageRepository.saveAll(images);
    }
    
}
