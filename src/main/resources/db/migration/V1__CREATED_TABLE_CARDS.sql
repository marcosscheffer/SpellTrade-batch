CREATE TYPE color AS ENUM ('W', 'U', 'B', 'R', 'G');

CREATE TYPE rarity_type AS ENUM (
    'common',
    'uncommon',
    'rare',
    'mythic'
);

CREATE TYPE formats AS ENUM (
    'standart', 
    'future', 
    'historic', 
    'timeless', 
    'gladiator', 
    'pioneer', 
    'modern', 
    'legacy', 
    'pauper', 
    'vintage', 
    'penny', 
    'commander', 
    'oathbreaker', 
    'standartbrawl', 
    'brawl', 
    'alchemy', 
    'paupercommander', 
    'duel', 
    'oldschool', 
    'premodern', 
    'preedh', 
    'tlr'
);

CREATE TYPE legality_status AS ENUM (
    'legal',
    'not_legal',
    'restricted',
    'banned'
);

CREATE TYPE set_type AS ENUM (
    'core',
    'expansion',
    'eternal',
    'masters',
    'masterpiece',
    'alchemy',
    'arsenal',
    'from_the_vault',
    'spellbook',
    'premium_deck',
    'duel_deck',
    'draft_innovation',
    'treasure_chest',
    'commander',
    'planechase',
    'archenemy',
    'vanguard',
    'funny',
    'starter',
    'box',
    'promo',
    'token',
    'memorabilia'
);

CREATE TABLE sets (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(10) NOT NULL,
    type set_type NOT NULL
);

CREATE TABLE cards (
    id UUID PRIMARY KEY,
    oracle_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    lang VARCHAR(10),
    mana_cost VARCHAR(50),
    released_at DATE NOT NULL,
    type_line varchar(100),
    oracle_text TEXT,
    color_identity color[],
    reserved BOOLEAN NOT NULL,
    set_id UUID NOT NULL,
    power VARCHAR(10), 
    toughness VARCHAR(10),
    loyalty VARCHAR(10),
    rarity rarity_type,

    CONSTRAINT fk_set
        FOREIGN KEY (set_id)
        REFERENCES sets(id)
);

CREATE TABLE card_faces(
    id BIGSERIAL PRIMARY KEY,
    card_id UUID NOT NULL,
    face_index SMALLINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    mana_cost VARCHAR(50),
    type_line VARCHAR(100),
    color_identity color[],
    oracle_text TEXT,

    CONSTRAINT fk_card_face
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
);

CREATE TABLE images (
    id BIGSERIAL PRIMARY KEY,
    card_id UUID,
    card_face BIGINT,
    small VARCHAR(255) NOT NULL,
    normal VARCHAR(255) NOT NULL,
    large VARCHAR(255) NOT NULL,
    png VARCHAR(255) NOT NULL,
    art_crop VARCHAR(255) NOT NULL,
    border_crop VARCHAR(255) NOT NULL,

    CONSTRAINT fk_images_card
        FOREIGN KEY (card_id)
        REFERENCES cards(id),

    CONSTRAINT fk_images_face
        FOREIGN KEY (card_face)
        REFERENCES card_faces(id),

    CONSTRAINT chck_one_owner 
        CHECK (
            (card_id IS NOT NULL AND card_face IS NULL)
            OR
            (card_id IS NULL AND card_face IS NOT NULL)
    )
);

CREATE TABLE card_legalities (
    card_id UUID NOT NULL,
    format formats NOT NULL,
    status legality_status NOT NULL,

    PRIMARY KEY (card_id, format),

    CONSTRAINT fk_card_legalities
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
);