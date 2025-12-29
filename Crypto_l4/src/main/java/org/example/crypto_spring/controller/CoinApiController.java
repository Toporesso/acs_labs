package org.example.crypto_spring.controller;

import org.example.crypto_spring.entity.CryptoCoin;
import org.example.crypto_spring.entity.Exchange;
import org.example.crypto_spring.service.CryptoCoinService;
import org.example.crypto_spring.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CoinApiController {

    @Autowired
    private CryptoCoinService coinService;

    @GetMapping(value = "/coins-xsl", produces = MediaType.APPLICATION_XML_VALUE)
    public String getCoinsWithXsl() {
        List<CryptoCoin> coins = coinService.getAllWithExchanges();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<?xml-stylesheet type=\"text/xsl\" href=\"/coins.xsl\"?>\n");
        xml.append("<List>\n");

        for (CryptoCoin coin : coins) {
            xml.append("  <item>\n");
            xml.append("    <id>").append(coin.getId()).append("</id>\n");
            xml.append("    <name>").append(coin.getName()).append("</name>\n");
            xml.append("    <symbol>").append(coin.getSymbol()).append("</symbol>\n");
            xml.append("    <priceUsd>").append(coin.getPriceUsd()).append("</priceUsd>\n");
            xml.append("    <marketCap>").append(coin.getMarketCap()).append("</marketCap>\n");

            xml.append("    <exchanges>\n");
            if (coin.getExchanges() != null && !coin.getExchanges().isEmpty()) {
                for (Exchange exchange : coin.getExchanges()) {
                    xml.append("      <exchange>\n");
                    xml.append("        <id>").append(exchange.getId()).append("</id>\n");
                    xml.append("        <name>").append(exchange.getName()).append("</name>\n");
                    xml.append("        <country>").append(exchange.getCountry()).append("</country>\n");
                    xml.append("        <website>").append(exchange.getWebsite()).append("</website>\n");
                    xml.append("      </exchange>\n");
                }
            }
            xml.append("    </exchanges>\n");

            xml.append("  </item>\n");
        }

        xml.append("</List>");
        return xml.toString();
    }

    @GetMapping(value = "/coins", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<CryptoCoin> getCoins() {
        return coinService.getAllWithExchanges();
    }

    @PostMapping(value = "/coins", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CryptoCoin createCoin(@RequestBody CryptoCoin coin) {
        return coinService.save(coin);
    }

    @PutMapping(value = "/coins/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CryptoCoin updateCoin(@PathVariable Long id, @RequestBody CryptoCoin coin) {
        coin.setId(id);
        return coinService.save(coin);
    }

    @DeleteMapping("/coins/{id}")
    public ResponseEntity<Void> deleteCoin(@PathVariable Long id) {
        coinService.delete(id);
        return ResponseEntity.noContent().build();
    }
}