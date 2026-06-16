ALTER TABLE color_identity
    DROP CONSTRAINT fk_color_identity_card,
    ADD CONSTRAINT fk_color_identity_card
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
        ON DELETE CASCADE;