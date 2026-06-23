ALTER TABLE card.images
    ADD CONSTRAINT uk_images_card_face
    UNIQUE (card_face, face_index);

ALTER TABLE card.card_faces
    ADD CONSTRAINT card_faces_card_face
    UNIQUE (card_id, face_index);

ALTER TABLE card.images
    ADD CONSTRAINT card_id_index
    UNIQUE (card_id, face_index);