ALTER TABLE cards.cards
    DROP CONSTRAINT fk_set,
    ADD CONSTRAINT fk_card_set
        FOREIGN KEY (set_id)
        REFERENCES cards.sets(id)
        ON DELETE CASCADE;

ALTER TABLE cards.card_faces
    DROP CONSTRAINT fk_card_face,
    ADD CONSTRAINT fk_card_face_card
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id)
        ON DELETE CASCADE;

ALTER TABLE cards.card_legalities
    DROP CONSTRAINT fk_card_legalities,
    add CONSTRAINT fk_card_legalities_card
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id)
        ON DELETE CASCADE;
    
ALTER TABLE cards.images
    DROP CONSTRAINT fk_images_card,
    ADD CONSTRAINT fk_image_card
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id)
        ON DELETE CASCADE,
    DROP CONSTRAINT fk_images_face,
    ADD CONSTRAINT fk_image_card_face
        FOREIGN KEY (card_face)
        REFERENCES cards.card_faces(id)
        ON DELETE CASCADE;

ALTER TABLE cards.color_identity
    DROP CONSTRAINT fk_color_identity_card,
    ADD CONSTRAINT fk_color_identity_card
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id)
        ON DELETE CASCADE;
