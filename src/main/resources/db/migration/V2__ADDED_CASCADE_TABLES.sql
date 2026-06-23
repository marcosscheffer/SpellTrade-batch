ALTER TABLE card.cards
    DROP CONSTRAINT fk_set,
    ADD CONSTRAINT fk_card_set
        FOREIGN KEY (set_id)
        REFERENCES card.sets(id)
        ON DELETE CASCADE;

ALTER TABLE card.card_faces
    DROP CONSTRAINT fk_card_face,
    ADD CONSTRAINT fk_card_face_card
        FOREIGN KEY (card_id)
        REFERENCES card.cards(id)
        ON DELETE CASCADE;

ALTER TABLE card.card_legalities
    DROP CONSTRAINT fk_card_legalities,
    add CONSTRAINT fk_card_legalities_card
        FOREIGN KEY (card_id)
        REFERENCES card.cards(id)
        ON DELETE CASCADE;
    
ALTER TABLE card.images
    DROP CONSTRAINT fk_images_card,
    ADD CONSTRAINT fk_image_card
        FOREIGN KEY (card_id)
        REFERENCES card.cards(id)
        ON DELETE CASCADE,
    DROP CONSTRAINT fk_images_face,
    ADD CONSTRAINT fk_image_card_face
        FOREIGN KEY (card_face)
        REFERENCES card.card_faces(id)
        ON DELETE CASCADE;

ALTER TABLE card.color_identity
    DROP CONSTRAINT fk_color_identity_card,
    ADD CONSTRAINT fk_color_identity_card
        FOREIGN KEY (card_id)
        REFERENCES card.cards(id)
        ON DELETE CASCADE;
