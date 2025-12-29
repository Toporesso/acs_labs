INSERT INTO exchange (name, country, website) VALUES
('Binance', 'Cayman Islands', 'https://binance.com'),
('Coinbase', 'USA', 'https://coinbase.com'),
('Kraken', 'USA', 'https://kraken.com'),
('Bitfinex', 'British Virgin Islands', 'https://bitfinex.com'),
('Huobi', 'Seychelles', 'https://huobi.com'),
('Gemini', 'USA', 'https://gemini.com'),
('OKX', 'Malta', 'https://okx.com'),
('KuCoin', 'Seychelles', 'https://kucoin.com');

INSERT INTO crypto_coin (name, symbol, price_usd, market_cap) VALUES
('Bitcoin', 'BTC', 50000.00, 980000000000),
('Ethereum', 'ETH', 3000.00, 360000000000),
('Binance Coin', 'BNB', 900.00, 15000000000),
('Cardano', 'ADA', 1.20, 40000000000),
('Ripple', 'XRP', 0.90, 42000000000),
('Solana', 'SOL', 25.00, 8500000000),
('Polkadot', 'DOT', 7.50, 8000000000),
('Dogecoin', 'DOGE', 0.07, 10000000000);

INSERT INTO exchange_cryptos (exchange_id, crypto_id) VALUES
(1, 3), -- Binance → Binance Coin (BNB)
(1, 4), -- Binance → Cardano (ADA)
(1, 5), -- Binance → Ripple (XRP)
(1, 6), -- Binance → Solana (SOL)
(1, 7), -- Binance → Polkadot (DOT)
(1, 8), -- Binance → Dogecoin (DOGE)

(2, 1), -- Kraken → Bitcoin (BTC)
(2, 2), -- Kraken → Ethereum (ETH)
(2, 4), -- Kraken → Cardano (ADA)
(2, 6), -- Kraken → Solana (SOL)
(2, 7), -- Kraken → Polkadot (DOT)
(2, 8), -- Kraken → Dogecoin (DOGE)

(3, 1), -- Kraken → Bitcoin (BTC)
(3, 2), -- Kraken → Ethereum (ETH)
(3, 4), -- Kraken → Cardano (ADA)
(3, 6), -- Kraken → Solana (SOL)
(3, 7), -- Kraken → Polkadot (DOT)
(3, 8), -- Kraken → Dogecoin (DOGE)

(4, 1), -- Bitfinex → Bitcoin (BTC)
(4, 5), -- Bitfinex → Ripple (XRP)
(4, 6), -- Bitfinex → Solana (SOL)
(4, 7), -- Bitfinex → Polkadot (DOT)
(4, 8), -- Bitfinex → Dogecoin (DOGE)
(4, 3), -- Bitfinex → Binance Coin (BNB)

(5, 2), -- Huobi → Ethereum (ETH)
(5, 3), -- Huobi → Binance Coin (BNB)
(5, 4), -- Huobi → Cardano (ADA)
(5, 5), -- Huobi → Ripple (XRP)
(5, 8), -- Huobi → Dogecoin (DOGE)
(5, 7), -- Huobi → Polkadot (DOT)

(6, 1), -- Gemini → Bitcoin (BTC)
(6, 2), -- Gemini → Ethereum (ETH)
(6, 3), -- Gemini → Binance Coin (BNB)
(6, 4), -- Gemini → Cardano (ADA)
(6, 5), -- Gemini → Ripple (XRP)
(6, 8), -- Gemini → Dogecoin (DOGE)

(7, 1), -- OKX → Bitcoin (BTC)
(7, 2), -- OKX → Ethereum (ETH)
(7, 6), -- OKX → Solana (SOL)
(7, 7), -- OKX → Polkadot (DOT)
(7, 3), -- OKX → Binance Coin (BNB)
(7, 5), -- OKX → Ripple (XRP)

(8, 2), -- KuCoin → Ethereum (ETH)
(8, 3), -- KuCoin → Binance Coin (BNB)
(8, 4), -- KuCoin → Cardano (ADA)
(8, 5), -- KuCoin → Ripple (XRP)
(8, 6), -- KuCoin → Solana (SOL)
(8, 7); -- KuCoin → Polkadot (DOT)