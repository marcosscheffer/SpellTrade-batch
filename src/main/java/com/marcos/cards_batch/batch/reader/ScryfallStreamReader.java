package com.marcos.cards_batch.batch.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScryfallStreamReader implements ItemReader<ScryfallCardDto>{

    private final ObjectMapper objectMapper;
    private JsonParser parser;
    
    public ScryfallStreamReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    private void init() throws IOException {
        log.info("Start reading cards");
        Path path = Paths.get("data", "cards.json");
        parser = null;
        parser = objectMapper.getFactory().createParser(Files.newInputStream(path));

        parser.nextToken();
    }

    @Override
    public @Nullable ScryfallCardDto read() throws Exception {
        if (parser == null) {
            init();
        }

        if (parser.nextToken() == JsonToken.END_ARRAY) {
            parser.close();
            parser = null; 
            return null;
        }

        return objectMapper.readValue(parser, ScryfallCardDto.class);
    }
}