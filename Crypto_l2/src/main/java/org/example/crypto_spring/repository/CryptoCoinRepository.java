package org.example.crypto_spring.repository;

import org.example.crypto_spring.entity.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, Long> {
    @Query("SELECT c FROM CryptoCoin c LEFT JOIN FETCH c.exchanges")
    List<CryptoCoin> findAllWithExchanges();
}
