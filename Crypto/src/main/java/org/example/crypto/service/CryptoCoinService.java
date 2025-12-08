package org.example.crypto.service;

import org.example.crypto.model.CryptoCoin;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CryptoCoinService {

    @PersistenceContext(unitName = "CryptoPU")
    private EntityManager em;

    public void add(CryptoCoin coin) {
        em.persist(coin);
    }

    public CryptoCoin find(Long id) {
        return em.find(CryptoCoin.class, id);
    }

    public void update(CryptoCoin coin) {
        em.merge(coin);
    }

    public void delete(Long id) {
        CryptoCoin coin = find(id);
        if (coin != null) em.remove(coin);
    }

    public List<CryptoCoin> getAll() {
        return em.createQuery("SELECT c FROM CryptoCoin c", CryptoCoin.class).getResultList();
    }
}
