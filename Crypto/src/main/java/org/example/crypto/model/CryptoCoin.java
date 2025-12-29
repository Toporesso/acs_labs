package org.example.crypto.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CryptoCoin")
public class CryptoCoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private BigDecimal marketCap;

    @Column(nullable = false)
    private BigDecimal priceUsd;

    @ManyToMany(mappedBy = "cryptos")
    private Set<Exchange> exchanges = new HashSet<>();

    public CryptoCoin() {}

    public CryptoCoin(String name, String symbol, BigDecimal priceUsd, BigDecimal marketCap) {
        this.name = name;
        this.symbol = symbol;
        this.priceUsd = priceUsd;
        this.marketCap = marketCap;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public BigDecimal getMarketCap() { return marketCap; }
    public void setMarketCap(BigDecimal marketCap) { this.marketCap = marketCap; }

    public BigDecimal getPriceUsd() { return priceUsd; }
    public void setPriceUsd(BigDecimal priceUsd) { this.priceUsd = priceUsd; }

    public Set<Exchange> getExchanges() { return exchanges; }
    public void setExchanges(Set<Exchange> exchanges) { this.exchanges = exchanges; }

    @Override
    public String toString() {
        return String.format("%s (%s) - Price: %s, MarketCap: %s", name, symbol, priceUsd, marketCap);
    }
}
