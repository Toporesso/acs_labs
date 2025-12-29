CREATE TABLE IF NOT EXISTS exchange (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    website VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS crypto_coin (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    symbol VARCHAR(255) NOT NULL,
    price_usd NUMERIC(19,2) NOT NULL,
    market_cap NUMERIC(19,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS exchange_cryptos (
    exchange_id BIGINT NOT NULL,
    crypto_id BIGINT NOT NULL,
    PRIMARY KEY (exchange_id, crypto_id),
    FOREIGN KEY (exchange_id) REFERENCES exchange(id) ON DELETE CASCADE,
    FOREIGN KEY (crypto_id) REFERENCES crypto_coin(id) ON DELETE CASCADE
);