package org.example.crypto_spring.service;

import org.example.crypto_spring.entity.Exchange;
import org.example.crypto_spring.entity.CryptoCoin;
import org.example.crypto_spring.repository.ExchangeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ExchangeService {
    private final ExchangeRepository exchangeRepository;

    public ExchangeService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public List<Exchange> getAll() {
        return exchangeRepository.findAll();
    }

    public List<Exchange> getAllWithCryptos() {
        return exchangeRepository.findAllWithCryptos();
    }

    public Exchange getById(Long id) {
        return exchangeRepository.findById(id).orElse(null);
    }

    public Exchange save(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    @Transactional
    public void delete(Long id) {
        Exchange exchange = getById(id);
        if (exchange != null) {
            Set<CryptoCoin> cryptos = exchange.getCryptos();
            for (CryptoCoin coin : cryptos) {
                coin.getExchanges().remove(exchange);
            }
            exchange.getCryptos().clear();
            exchangeRepository.deleteById(id);
        }
    }
}