package org.example.crypto.service;

import org.example.crypto.model.Exchange;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ExchangeService {

    @PersistenceContext(unitName = "CryptoPU")
    private EntityManager em;

    public List<Exchange> getAll() {
        return em.createQuery("SELECT DISTINCT e FROM Exchange e LEFT JOIN FETCH e.cryptos",
                Exchange.class).getResultList();
    }

    public void add(Exchange exchange) {
        em.persist(exchange);
    }

    public void update(Exchange exchange) {
        em.merge(exchange);
    }

    public void delete(Long id) {
        Exchange exchange = em.find(Exchange.class, id);
        if (exchange != null) em.remove(exchange);
    }

    public Exchange getById(Long id) {
        return em.find(Exchange.class, id);
    }
}
