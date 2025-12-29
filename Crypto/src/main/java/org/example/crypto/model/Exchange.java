package org.example.crypto.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Exchange")
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String country;
    private String website;

    @ManyToMany
    @JoinTable(
            name = "ExchangeCrypto",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "crypto_id")
    )
    private Set<CryptoCoin> cryptos = new HashSet<>();

    public Exchange() {}

    public Exchange(String name, String country, String website) {
        this.name = name;
        this.country = country;
        this.website = website;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public Set<CryptoCoin> getCryptos() { return cryptos; }
    public void setCryptos(Set<CryptoCoin> cryptos) { this.cryptos = cryptos; }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", name, country != null ? country : "N/A", website != null ? website : "N/A");
    }
}
