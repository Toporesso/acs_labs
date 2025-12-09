package org.example.crypto_spring.service;

import org.example.crypto_spring.entity.CryptoCoin;
import org.example.crypto_spring.entity.Exchange;
import org.example.crypto_spring.repository.CryptoCoinRepository;
import org.example.crypto_spring.repository.ExchangeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CryptoCoinService {
    private final CryptoCoinRepository coinRepository;
    private final ExchangeRepository exchangeRepository;

    public CryptoCoinService(CryptoCoinRepository coinRepository, ExchangeRepository exchangeRepository) {
        this.coinRepository = coinRepository;
        this.exchangeRepository = exchangeRepository;
    }

    public List<CryptoCoin> getAll() {
        return coinRepository.findAll();
    }

    public CryptoCoin getById(Long id) {
        return coinRepository.findById(id).orElse(null);
    }

    @Transactional
    public CryptoCoin save(CryptoCoin coin) {
        if (coin.getId() == null) {
            CryptoCoin savedCoin = coinRepository.save(coin);

            if (coin.getExchanges() != null) {
                for (Exchange exchange : coin.getExchanges()) {
                    exchange.getCryptos().add(savedCoin);
                    exchangeRepository.save(exchange);
                }
            }

            return savedCoin;
        }

        CryptoCoin existing = coinRepository.findById(coin.getId()).orElseThrow();

        Set<Exchange> oldExchanges = new HashSet<>(existing.getExchanges());

        existing.setName(coin.getName());
        existing.setSymbol(coin.getSymbol());
        existing.setPriceUsd(coin.getPriceUsd());
        existing.setMarketCap(coin.getMarketCap());

        if (coin.getExchanges() != null) {
            for (Exchange oldExchange : oldExchanges) {
                oldExchange.getCryptos().remove(existing);
                exchangeRepository.save(oldExchange);
            }

            existing.getExchanges().clear();
            existing.getExchanges().addAll(coin.getExchanges());

            // Добавляем монету к новым биржам
            for (Exchange newExchange : coin.getExchanges()) {
                newExchange.getCryptos().add(existing);
                exchangeRepository.save(newExchange);
            }
        } else {
            for (Exchange oldExchange : oldExchanges) {
                oldExchange.getCryptos().remove(existing);
                exchangeRepository.save(oldExchange);
            }
            existing.getExchanges().clear();
        }

        return coinRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        CryptoCoin coin = getById(id);
        if (coin != null) {
            for (Exchange exchange : coin.getExchanges()) {
                exchange.getCryptos().remove(coin);
                exchangeRepository.save(exchange);
            }
            coinRepository.deleteById(id);
        }
    }

    public List<CryptoCoin> getAllWithExchanges() {
        return coinRepository.findAllWithExchanges();
    }
}