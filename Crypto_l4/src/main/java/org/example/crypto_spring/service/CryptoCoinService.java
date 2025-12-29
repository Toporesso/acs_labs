package org.example.crypto_spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Transactional
public class CryptoCoinService {
    private final CryptoCoinRepository coinRepository;
    private final ExchangeRepository exchangeRepository;
    private final AuditService auditService;

    public CryptoCoinService(CryptoCoinRepository coinRepository,
                             ExchangeRepository exchangeRepository,
                             AuditService auditService) {
        this.coinRepository = coinRepository;
        this.exchangeRepository = exchangeRepository;
        this.auditService = auditService;
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
            try {
                auditService.sendAuditEvent("INSERT", "CryptoCoin", savedCoin.getId(), savedCoin);
            } catch (Exception e) {
                System.err.println("Ошибка при отправке аудит-события: " + e.getMessage());
            }

            return savedCoin;
        }

        CryptoCoin existing = coinRepository.findById(coin.getId()).orElseThrow();

        String oldName = existing.getName();
        String oldSymbol = existing.getSymbol();
        String oldPrice = existing.getPriceUsd() != null ? existing.getPriceUsd().toString() : "null";
        String oldMarketCap = existing.getMarketCap() != null ? existing.getMarketCap().toString() : "null";

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

        CryptoCoin updatedCoin = coinRepository.save(existing);

        try {
            auditService.sendAuditEvent("UPDATE", "CryptoCoin", updatedCoin.getId(), updatedCoin);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке события: " + e.getMessage());
        }

        return updatedCoin;
    }

    @Transactional
    public void delete(Long id) {
        CryptoCoin coin = getById(id);
        if (coin != null) {
            try {
                auditService.sendAuditEvent("DELETE", "CryptoCoin", coin.getId(), coin);
            } catch (Exception e) {
                System.err.println("Ошибка при отправке события: " + e.getMessage());
            }

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