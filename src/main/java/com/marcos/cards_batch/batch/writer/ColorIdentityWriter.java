package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.ColorIdentityJdbc;

@Component
public class ColorIdentityWriter implements ItemWriter<ColorIdentityJdbc> {
    private final JdbcTemplate jdbcTemplate;

    public ColorIdentityWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends ColorIdentityJdbc> chunk) throws Exception {
        List<? extends ColorIdentityJdbc> items = chunk.getItems();

        String sql = """
                INSERT INTO color_identity
                ("card_id", "color")
                VALUES
                (?, ?)
                ON CONFLICT (card_id, color) DO NOTHING;
                """;

        jdbcTemplate.batchUpdate(
            sql,
            items,
            chunk.size(),
            (ps, colorIdentity) -> {
                ps.setObject(1, colorIdentity.getCardId());
                ps.setString(2, colorIdentity.getColor().name());
            }
        );
    }
}
