package com.marcos.cards_batch.batch.listener;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SkipCardListener implements SkipListener<ScryfallCardDto, CardJdbc> {

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("Reading error - {}", t);
    }

    @Override
    public void onSkipInProcess(ScryfallCardDto item, Throwable t) {
        log.error("Skipping process. Card {} - reason {}", item.id(), t);
    }

    @Override
    public void onSkipInWrite(CardJdbc item, Throwable t) {
        log.error("Skipping write. Card {} - reason {}", item.getId(), t);
    }
}
