package com.marcos.cards_batch.batch.listener;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.ImageJdbc;
import com.marcos.cards_batch.dto.ImageDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SkipImageListener implements SkipListener<ImageDto, ImageJdbc>{

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("Reading error - {}", t);
    }

    @Override
    public void onSkipInProcess(ImageDto item, Throwable t) {
        log.error("Skipping process. Image {} - reason {}", item.normal(), t);
    }

    @Override
    public void onSkipInWrite(ImageJdbc item, Throwable t) {
        log.error("Skipping process. Image {} - reason {}", item.getNormal(), t);
    }
    
}
