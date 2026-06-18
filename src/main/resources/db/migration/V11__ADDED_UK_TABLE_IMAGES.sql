ALTER TABLE images
    ADD CONSTRAINT card_id_index
    UNIQUE (card_id, face_index);