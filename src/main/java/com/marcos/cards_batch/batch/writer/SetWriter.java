package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardSetJdbc;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SetWriter implements ItemWriter<CardSetJdbc>{
    private final JdbcTemplate jdbcTemplate;

    public SetWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends CardSetJdbc> chunk) throws Exception {
        List<? extends CardSetJdbc> items = chunk.getItems();
        String sql = """
                INSERT INTO sets
                ("id", "name", "code", "type")
                VALUES
                (?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING;
                """;
        
        jdbcTemplate.batchUpdate(
            sql,
            items,
            chunk.size(),
            (ps, cardSetJdbc) -> {
                ps.setObject(1, cardSetJdbc.getId());
                ps.setString(2, cardSetJdbc.getName());
                ps.setString(3, cardSetJdbc.getCode());
                ps.setString(4, cardSetJdbc.getType().name());
            }
        );
    }
}
