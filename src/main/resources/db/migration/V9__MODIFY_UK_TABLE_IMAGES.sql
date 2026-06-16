ALTER TABLE images
    ADD COLUMN face_index SMALLINT NOT NULL DEFAULT 0,
    ADD CONSTRAINT uk_images_card_face
    UNIQUE (card_face, face_index);