TRUNCATE TABLE ExchangeCrypto CASCADE;
TRUNCATE TABLE Exchange CASCADE;
TRUNCATE TABLE CryptoCoin CASCADE;

ALTER SEQUENCE IF EXISTS cryptocoin_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS exchange_id_seq RESTART WITH 1;

INSERT INTO CryptoCoin (name, symbol, priceUsd, marketCap) VALUES
('Bitcoin', 'BTC', 67000.50, 1320000000000),
('Ethereum', 'ETH', 3500.25, 420000000000),
('Cardano', 'ADA', 0.45, 16000000000),
('Solana', 'SOL', 170.80, 75000000000),
('Polkadot', 'DOT', 7.20, 9200000000),
('Ripple', 'XRP', 0.52, 28000000000),
('Dogecoin', 'DOGE', 0.15, 21000000000),
('Litecoin', 'LTC', 82.30, 6100000000),
('Chainlink', 'LINK', 14.75, 8500000000),
('Polygon', 'MATIC', 0.72, 7100000000);

INSERT INTO Exchange (name, country, website) VALUES
('Binance', 'Global', 'https://www.binance.com'),
('Coinbase', 'USA', 'https://www.coinbase.com'),
('Kraken', 'USA', 'https://www.kraken.com'),
('Huobi', 'Singapore', 'https://www.huobi.com'),
('KuCoin', 'Seychelles', 'https://www.kucoin.com'),
('Bybit', 'Singapore', 'https://www.bybit.com'),
('OKX', 'Global', 'https://www.okx.com'),
('Bitfinex', 'British Virgin Islands', 'https://www.bitfinex.com');

INSERT INTO ExchangeCrypto (exchange_id, crypto_id)
-- Binance
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'Binance' AND c.name IN ('Bitcoin', 'Ethereum', 'Cardano', 'Solana', 'Polkadot', 'Ripple', 'Dogecoin', 'Litecoin', 'Chainlink', 'Polygon')
UNION ALL
-- Coinbase
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'Coinbase' AND c.name IN ('Bitcoin', 'Ethereum', 'Cardano', 'Solana', 'Polkadot', 'Ripple')
UNION ALL
-- Kraken
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'Kraken' AND c.name IN ('Bitcoin', 'Ethereum', 'Solana', 'Ripple', 'Litecoin', 'Chainlink')
UNION ALL
-- Huobi
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'Huobi' AND c.name IN ('Bitcoin', 'Ethereum', 'Cardano', 'Solana', 'Ripple', 'Dogecoin')
UNION ALL
-- KuCoin
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'KuCoin' AND c.name IN ('Bitcoin', 'Ethereum', 'Cardano', 'Solana', 'Polkadot', 'Ripple', 'Dogecoin', 'Chainlink', 'Polygon')
UNION ALL
-- Bybit
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'Bybit' AND c.name IN ('Bitcoin', 'Ethereum', 'Solana', 'Ripple')
UNION ALL
-- OKX
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'OKX' AND c.name IN ('Bitcoin', 'Ethereum', 'Cardano', 'Solana', 'Polkadot', 'Ripple')
UNION ALL
-- Bitfinex
SELECT e.id, c.id FROM Exchange e, CryptoCoin c
WHERE e.name = 'Bitfinex' AND c.name IN ('Bitcoin', 'Ethereum', 'Solana', 'Chainlink');

COMMIT;