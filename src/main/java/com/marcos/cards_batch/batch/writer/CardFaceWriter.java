package com.marcos.cards_batch.batch.writer;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardFaceJdbc;
import java.util.List;

@Component
public class CardFaceWriter implements ItemWriter<CardFaceJdbc> {
    private final JdbcTemplate jdbcTemplate;

    public CardFaceWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends CardFaceJdbc> chunk) throws Exception {
        List<? extends CardFaceJdbc> items = chunk.getItems();
        String sql = """
                INSERT INTO card_faces
                ("card_id", "face_index", "name", "mana_cost", "type_line", "oracle_text", "power", "toughness", "loyalty")
                VALUES 
                (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (card_id, face_index) DO NOTHING;
                """;
        
        jdbcTemplate.batchUpdate(
            sql,
            items,
            chunk.size(),
            (ps, cardFace) -> {
                ps.setObject(1, cardFace.getCardId());
                ps.setShort(2, cardFace.getFaceIndex());
                ps.setString(3, cardFace.getName());
                ps.setString(4, cardFace.getManaCost());
                ps.setString(5, cardFace.getTypeLine());
                ps.setString(6, cardFace.getOracleText());
                ps.setString(7, cardFace.getPower());
                ps.setString(8, cardFace.getToughness());
                ps.setString(9, cardFace.getLoyalty());
            }
        );
    }
}
