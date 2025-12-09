package org.example.crypto_spring.controller;

import org.example.crypto_spring.entity.CryptoCoin;
import org.example.crypto_spring.entity.Exchange;
import org.example.crypto_spring.service.CryptoCoinService;
import org.example.crypto_spring.service.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coins")
public class CryptoCoinController {

    private final CryptoCoinService service;
    private final ExchangeService exchangeService;

    public CryptoCoinController(CryptoCoinService service, ExchangeService exchangeService) {
        this.service = service;
        this.exchangeService = exchangeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("coins", service.getAllWithExchanges());
        return "coins-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("coin", new CryptoCoin());
        model.addAttribute("allExchanges", exchangeService.getAll());
        return "coin-form";
    }

    @PostMapping("/add")
    public String saveCoin(@ModelAttribute CryptoCoin coin,
                           @RequestParam(required = false) List<Long> exchangeIds) {
        updateCoinExchanges(coin, exchangeIds);
        service.save(coin);
        return "redirect:/coins";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("coin", service.getById(id));
        model.addAttribute("allExchanges", exchangeService.getAll());
        return "coin-form";
    }

    @PostMapping("/edit")
    public String updateCoin(@ModelAttribute CryptoCoin coin,
                             @RequestParam(required = false) List<Long> exchangeIds) {
        updateCoinExchanges(coin, exchangeIds);
        service.save(coin);
        return "redirect:/coins";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/coins";
    }

    // Вспомогательный метод для обновления связей с биржами
    private void updateCoinExchanges(CryptoCoin coin, List<Long> exchangeIds) {
        if (exchangeIds != null) {
            Set<Exchange> exchanges = exchangeIds.stream()
                    .map(id -> exchangeService.getById(id))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            coin.setExchanges(exchanges);

            for (Exchange exchange : exchanges) {
                exchange.getCryptos().add(coin);
            }
        } else {
            coin.setExchanges(new HashSet<>());
        }
    }
}