package org.example.crypto_spring.service;

import org.example.crypto_spring.entity.Exchange;
import org.example.crypto_spring.entity.CryptoCoin;
import org.example.crypto_spring.repository.ExchangeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExchangeService {
    private final ExchangeRepository exchangeRepository;
    private final AuditService auditService;

    public ExchangeService(ExchangeRepository exchangeRepository,
                           AuditService auditService) {
        this.exchangeRepository = exchangeRepository;
        this.auditService = auditService;
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
        boolean isNew = exchange.getId() == null;
        Exchange savedExchange = exchangeRepository.save(exchange);

        try {
            String eventType = isNew ? "INSERT" : "UPDATE";
            auditService.sendAuditEvent(eventType, "Exchange", savedExchange.getId(), savedExchange);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке события: " + e.getMessage());
        }

        return savedExchange;
    }

    @Transactional
    public void delete(Long id) {
        Exchange exchange = getById(id);
        if (exchange != null) {
            try {
                auditService.sendAuditEvent("DELETE", "Exchange", exchange.getId(), exchange);
            } catch (Exception e) {
                System.err.println("Ошибка при отправке события: " + e.getMessage());
            }

            Set<CryptoCoin> cryptos = exchange.getCryptos();
            for (CryptoCoin coin : cryptos) {
                coin.getExchanges().remove(exchange);
            }
            exchange.getCryptos().clear();
            exchangeRepository.deleteById(id);
        }
    }
}