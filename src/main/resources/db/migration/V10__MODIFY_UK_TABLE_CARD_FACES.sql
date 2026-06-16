ALTER TABLE card_faces
    ADD CONSTRAINT card_faces_card_face
    UNIQUE (card_id, face_index);