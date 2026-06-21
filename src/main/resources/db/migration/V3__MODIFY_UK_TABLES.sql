ALTER TABLE cards.images
    ADD CONSTRAINT uk_images_card_face
    UNIQUE (card_face, face_index);

ALTER TABLE cards.card_faces
    ADD CONSTRAINT card_faces_card_face
    UNIQUE (card_id, face_index);

ALTER TABLE cards.images
    ADD CONSTRAINT card_id_index
    UNIQUE (card_id, face_index);