package com.marcos.cards_batch.batch.writer;

import java.sql.Date;
import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardJdbc;


@Component
public class CardWriter implements ItemWriter<CardJdbc>{
    private final JdbcTemplate jdbcTemplate;

    public CardWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends CardJdbc> chunk) throws Exception {
        List<? extends CardJdbc> items = chunk.getItems();
        
        String sql = """
                INSERT INTO card.cards
                ("id", "oracle_id", "name", "lang", "mana_cost", "released_at", 
                "type_line", "oracle_text", "reserved", "set_id", "power", "toughness", 
                "loyalty", "rarity")
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING;
                """;
        
        jdbcTemplate.batchUpdate(
            sql,
            items,
            chunk.size(),
            (ps, cardJdbc) -> {
                ps.setObject(1, cardJdbc.getId());
                ps.setObject(2, cardJdbc.getOracleId());
                ps.setString(3, cardJdbc.getName());
                ps.setString(4, cardJdbc.getLang());
                ps.setString(5, cardJdbc.getManaCost());
                ps.setDate(6, Date.valueOf(cardJdbc.getReleasedAt()));
                ps.setString(7, cardJdbc.getTypeLine());
                ps.setString(8, cardJdbc.getOracleText());
                ps.setBoolean(9, cardJdbc.getReserved());
                ps.setObject(10, cardJdbc.getSetId());
                ps.setString(11, cardJdbc.getPower());
                ps.setString(12, cardJdbc.getToughness());
                ps.setString(13, cardJdbc.getLoyalty());
                ps.setString(14, cardJdbc.getRarity().name());
            }
        );
    }
}
