package com.marcos.cards_batch.batch.listener;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardFaceJdbc;
import com.marcos.cards_batch.dto.CardFacesDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SkipCardFaceListener implements SkipListener<CardFacesDto, CardFaceJdbc> {

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("Reading error - {}", t);
    }

    @Override
    public void onSkipInProcess(CardFacesDto item, Throwable t) {
        log.error("Skipping process. CardFace {} - reason {}", item.name(), t);
    }

    @Override
    public void onSkipInWrite(CardFaceJdbc item, Throwable t) {
        log.error("Skipping process. CardFace {} - reason {}", item.getCardId(), t);
    }
    
}