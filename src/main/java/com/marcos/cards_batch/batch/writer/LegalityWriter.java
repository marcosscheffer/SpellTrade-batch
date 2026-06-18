package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardLegalityJdbc;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class LegalityWriter implements ItemWriter<CardLegalityJdbc> {
    private final JdbcTemplate jdbcTemplate;

    public LegalityWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void write(Chunk<? extends CardLegalityJdbc> chunk) throws Exception {
        List<? extends CardLegalityJdbc> items = chunk.getItems();
        String sql = """
                INSERT INTO card_legalities
                ("card_id", "format", "status")
                VALUES
                (?, ?, ?)
                ON CONFLICT (card_id, format) DO NOTHING;
                """;
        
        jdbcTemplate.batchUpdate(
            sql,
            items,
            chunk.size(),
            (ps, cardLegality) -> {
                ps.setObject(1, cardLegality.getCardId());
                ps.setString(2, cardLegality.getFormat().name());
                ps.setString(3, cardLegality.getStatus().name());
            }
        );
    } 
}
