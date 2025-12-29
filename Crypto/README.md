# Практическая работа 1. JakartaEE

### Задание 1

Запуск glassfish:
<img width="1518" height="748" alt="image" src="https://github.com/user-attachments/assets/21bed5c2-e0c4-43cc-a895-58974e367112" />

Панель glassfish:
<img width="1516" height="653" alt="image" src="https://github.com/user-attachments/assets/3d90e13d-7f30-4cf6-a298-e3b75ce90c48" />

### Задание 2

Использовалась postgreSQL

Панель pgAdmin 4:
<img width="1225" height="540" alt="image" src="https://github.com/user-attachments/assets/dd9f7d44-6cc3-4463-972b-d771225d1ae6" />

### Задание 3

В качестве темы была взята предметная область: Криптовалюта. Существует 2 сущности: Монеты и Биржи.

Скрипт для заполнения БД:

```
TRUNCATE TABLE ExchangeCrypto CASCADE;
TRUNCATE TABLE Exchange CASCADE;
TRUNCATE TABLE CryptoCoin CASCADE;

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

INSERT INTO ExchangeCrypto (exchange_id, crypto_id) VALUES
-- Binance
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),

-- Coinbase
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6),

-- Kraken
(3, 1), (3, 2), (3, 4), (3, 6), (3, 8), (3, 9),

-- Huobi
(4, 1), (4, 2), (4, 3), (4, 4), (4, 6), (4, 7),

-- KuCoin
(5, 1), (5, 2), (5, 3), (5, 4), (5, 5), (5, 6), (5, 7), (5, 9), (5, 10),

-- Bybit
(6, 1), (6, 2), (6, 4), (6, 6),

-- OKX
(7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6),

-- Bitfinex
(8, 1), (8, 2), (8, 4), (8, 9);

COMMIT;

SELECT 'CryptoCoins count: ' || COUNT(*) FROM CryptoCoin;
SELECT 'Exchanges count: ' || COUNT(*) FROM Exchange;
SELECT 'Exchange-Crypto links: ' || COUNT(*) FROM ExchangeCrypto;
```

### Задание 4

### Задание 5

### Задание 6

### Задание 7

Запускаем проект и видим пустые таблицы:
<img width="1089" height="432" alt="image" src="https://github.com/user-attachments/assets/c11465ef-ecda-40a7-acf9-34b1104aee8b" />
<img width="407" height="370" alt="image" src="https://github.com/user-attachments/assets/69e8afce-8773-4772-9c8d-a37d73a8c497" />

Для начала убидимся в правильности работы

Создадим биржу через форму:
<img width="254" height="161" alt="image" src="https://github.com/user-attachments/assets/e9937e3f-ea86-44cb-9a06-7de7457a51dc" />

Получим:
<img width="284" height="121" alt="image" src="https://github.com/user-attachments/assets/96632459-8c18-473c-98af-960d9db9e045" />

Создадим монету через форму:
<img width="359" height="224" alt="image" src="https://github.com/user-attachments/assets/26ba0872-af89-4dfc-80b8-b00b766f19b5" />

Получим:
<img width="1053" height="153" alt="image" src="https://github.com/user-attachments/assets/fb02813a-14d9-42ae-b8c7-87df32f6e232" />

Теперь заполним таблицу через SQL файл и получим:
<img width="1051" height="1290" alt="image" src="https://github.com/user-attachments/assets/26cb8ac1-a848-4485-99ce-c9722a0006f8" />
<img width="347" height="441" alt="image" src="https://github.com/user-attachments/assets/77fc4d75-ece0-4b8a-b95f-aa93b3422c6c" />

