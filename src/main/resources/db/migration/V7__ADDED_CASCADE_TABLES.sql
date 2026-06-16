ALTER TABLE color_identity
    ALTER COLUMN color TYPE VARCHAR(10)
    USING color::VARCHAR(10);

DROP TYPE color;
DROP TYPE formats;
DROP TYPE legality_status;
DROP TYPE rarity_type;
DROP TYPE set_type;


ALTER TABLE cards
    DROP CONSTRAINT fk_set,
    ADD CONSTRAINT fk_card_set
        FOREIGN KEY (set_id)
        REFERENCES sets(id)
        ON DELETE CASCADE;

ALTER TABLE card_faces
    DROP CONSTRAINT fk_card_face,
    ADD CONSTRAINT fk_card_face_card
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
        ON DELETE CASCADE;

ALTER TABLE card_legalities
    DROP CONSTRAINT fk_card_legalities,
    add CONSTRAINT fk_card_legalities_card
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
        ON DELETE CASCADE;
    
ALTER TABLE images
    DROP CONSTRAINT fk_images_card,
    ADD CONSTRAINT fk_image_card
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
        ON DELETE CASCADE,
    DROP CONSTRAINT fk_images_face,
    ADD CONSTRAINT fk_image_card_face
        FOREIGN KEY (card_face)
        REFERENCES card_faces(id)
        ON DELETE CASCADE;

