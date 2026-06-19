package com.marcos.cards_batch.batch.listener;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardSetJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SkipSetListener implements SkipListener<ScryfallCardDto, CardSetJdbc> {

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("Reading error - {}", t);
    }

    @Override
    public void onSkipInProcess(ScryfallCardDto item, Throwable t) {
        log.error("Skipping process. set {} - reason {}", item.setId(), t);
    }


    @Override
    public void onSkipInWrite(CardSetJdbc item, Throwable t) {
        log.error("Skipping process. set {} - reason {}", item.getId(), t);
    }
}
