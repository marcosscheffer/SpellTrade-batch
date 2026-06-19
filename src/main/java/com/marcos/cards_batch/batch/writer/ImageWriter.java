package com.marcos.cards_batch.batch.writer;

import java.sql.Types;
import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.ImageJdbc;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class ImageWriter implements ItemWriter<ImageJdbc>{
    private final JdbcTemplate jdbcTemplate;
    public ImageWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void write(Chunk<? extends ImageJdbc> chunk) throws Exception {
        List<? extends ImageJdbc> items = chunk.getItems();

        String sql = """
                INSERT INTO images
                ("card_id", "card_face", "small", "normal", "large", "png", "art_crop", "border_crop", "face_index")
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT DO NOTHING;
                """;

        jdbcTemplate.batchUpdate(
            sql,
            items,
            chunk.size(),
            (ps, image) -> {
                if (image.getCardId() == null) {
                    ps.setNull(1, Types.OTHER);
                } else {
                    ps.setObject(1, image.getCardId());
                }
                if (image.getCardFace() == null) {
                    ps.setNull(2, Types.BIGINT);
                } else {
                    ps.setLong(2, image.getCardFace());
                }
                ps.setString(3, image.getSmall());
                ps.setString(4, image.getNormal());
                ps.setString(5, image.getLarge());
                ps.setString(6, image.getPng());
                ps.setString(7, image.getArtCrop());
                ps.setString(8, image.getBorderCrop());
                ps.setShort(9, image.getFaceIndex());
            }
        );
    }
}
