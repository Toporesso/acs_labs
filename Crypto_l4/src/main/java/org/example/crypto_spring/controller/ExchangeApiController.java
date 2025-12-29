package org.example.crypto_spring.controller;

import org.example.crypto_spring.entity.CryptoCoin;
import org.example.crypto_spring.entity.Exchange;
import org.example.crypto_spring.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExchangeApiController {

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping(value = "/exchanges", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Exchange> getExchanges() {
        return exchangeService.getAllWithCryptos();
    }

    @GetMapping(value = "/exchanges-xsl", produces = MediaType.APPLICATION_XML_VALUE)
    public String getExchangesWithXsl() {
        List<Exchange> exchanges = exchangeService.getAllWithCryptos();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<?xml-stylesheet type=\"text/xsl\" href=\"/exchanges.xsl\"?>\n");
        xml.append("<List>\n");

        for (Exchange exchange : exchanges) {
            xml.append("  <item>\n");
            xml.append("    <id>").append(exchange.getId()).append("</id>\n");
            xml.append("    <name>").append(exchange.getName()).append("</name>\n");
            xml.append("    <country>").append(exchange.getCountry()).append("</country>\n");
            xml.append("    <website>").append(exchange.getWebsite()).append("</website>\n");

            xml.append("    <cryptos>\n");
            if (exchange.getCryptos() != null && !exchange.getCryptos().isEmpty()) {
                for (CryptoCoin coin : exchange.getCryptos()) {
                    xml.append("      <cryptoCoin>\n");
                    xml.append("        <id>").append(coin.getId()).append("</id>\n");
                    xml.append("        <name>").append(coin.getName()).append("</name>\n");
                    xml.append("        <symbol>").append(coin.getSymbol()).append("</symbol>\n");
                    xml.append("        <priceUsd>").append(coin.getPriceUsd()).append("</priceUsd>\n");
                    xml.append("        <marketCap>").append(coin.getMarketCap()).append("</marketCap>\n");
                    xml.append("      </cryptoCoin>\n");
                }
            }
            xml.append("    </cryptos>\n");

            xml.append("  </item>\n");
        }

        xml.append("</List>");
        return xml.toString();
    }

    @GetMapping(value = "/exchanges/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Exchange> getExchangeById(@PathVariable Long id) {
        Exchange exchange = exchangeService.getById(id);
        return exchange != null ? ResponseEntity.ok(exchange) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/exchanges", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Exchange> createExchange(@RequestBody Exchange exchange) {
        Exchange savedExchange = exchangeService.save(exchange);
        return ResponseEntity.ok(savedExchange);
    }
    @PutMapping(value = "/exchanges/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Exchange updateExchange(@PathVariable Long id, @RequestBody Exchange exchange) {
        exchange.setId(id);
        return exchangeService.save(exchange);
    }

    @DeleteMapping("/exchanges/{id}")
    public ResponseEntity<Void> deleteExchange(@PathVariable Long id) {
        exchangeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}