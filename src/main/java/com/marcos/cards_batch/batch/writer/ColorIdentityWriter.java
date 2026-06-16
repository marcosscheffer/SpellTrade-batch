package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.ColorIdentity;
import com.marcos.cards_batch.repository.ColorIdentityRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ColorIdentityWriter implements ItemWriter<List<ColorIdentity>> {
    private final ColorIdentityRepository colorIdentityRepository;

    public ColorIdentityWriter(ColorIdentityRepository colorIdentityRepository) {
        this.colorIdentityRepository = colorIdentityRepository;
    }

    @Override
    public void write(Chunk<? extends List<ColorIdentity>> chunk) throws Exception {
        List<? extends List<ColorIdentity>> items = chunk.getItems();
        List<ColorIdentity> colorIdentities = items.stream()
            .flatMap(list -> list.stream())
            .toList();
        log.info("Saving color identities - {} B", colorIdentities.size());
        colorIdentityRepository.saveAll(colorIdentities);
    }
    
}
