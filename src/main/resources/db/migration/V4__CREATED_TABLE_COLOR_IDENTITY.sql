CREATE TABLE color_identity (
    card_id UUID NOT NULL,
    color color NOT NULL,
    PRIMARY KEY(card_id, color),

    CONSTRAINT fk_color_identity_card
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
);

ALTER TABLE cards 
    DROP COLUMN color_identity;

ALTER TABLE card_faces 
    DROP COLUMN colors;

