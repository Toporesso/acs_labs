package org.example.crypto_spring.repository;

import org.example.crypto_spring.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    @Query("SELECT DISTINCT e FROM Exchange e LEFT JOIN FETCH e.cryptos")
    List<Exchange> findAllWithCryptos();
}
