CREATE SCHEMA IF NOT EXISTS cards;

CREATE TABLE cards.sets (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(10) NOT NULL,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE cards.cards (
    id UUID PRIMARY KEY,
    oracle_id UUID,
    name VARCHAR(255) NOT NULL,
    lang VARCHAR(10),
    mana_cost VARCHAR(50),
    released_at DATE NOT NULL,
    type_line varchar(100),
    oracle_text TEXT,
    reserved BOOLEAN NOT NULL,
    set_id UUID NOT NULL,
    power VARCHAR(10), 
    toughness VARCHAR(10),
    loyalty VARCHAR(10),
    rarity VARCHAR(50),

    CONSTRAINT fk_set
        FOREIGN KEY (set_id)
        REFERENCES cards.sets(id)
);

CREATE TABLE cards.card_faces(
    id BIGSERIAL PRIMARY KEY,
    card_id UUID NOT NULL,
    face_index SMALLINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    mana_cost VARCHAR(50),
    type_line VARCHAR(100),
    oracle_text TEXT,
    power VARCHAR(10), 
    toughness VARCHAR(10),
    loyalty VARCHAR(10),

    CONSTRAINT fk_card_face
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id)
);

CREATE TABLE cards.images (
    id BIGSERIAL PRIMARY KEY,
    card_id UUID,
    card_face BIGINT,
    face_index SMALLINT NOT NULL DEFAULT 0,
    small VARCHAR(255) NOT NULL,
    normal VARCHAR(255) NOT NULL,
    large VARCHAR(255) NOT NULL,
    png VARCHAR(255) NOT NULL,
    art_crop VARCHAR(255) NOT NULL,
    border_crop VARCHAR(255) NOT NULL,

    CONSTRAINT fk_images_card
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id),

    CONSTRAINT fk_images_face
        FOREIGN KEY (card_face)
        REFERENCES cards.card_faces(id),

    CONSTRAINT chck_one_owner 
        CHECK (
            (card_id IS NOT NULL AND card_face IS NULL)
            OR
            (card_id IS NULL AND card_face IS NOT NULL)
    )
);

CREATE TABLE cards.card_legalities (
    card_id UUID NOT NULL,
    format VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,

    PRIMARY KEY (card_id, format),

    CONSTRAINT fk_card_legalities
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id)
);

CREATE TABLE cards.color_identity (
    card_id UUID NOT NULL,
    color VARCHAR(50) NOT NULL,
    PRIMARY KEY(card_id, color),

    CONSTRAINT fk_color_identity_card
        FOREIGN KEY (card_id)
        REFERENCES cards.cards(id)
);