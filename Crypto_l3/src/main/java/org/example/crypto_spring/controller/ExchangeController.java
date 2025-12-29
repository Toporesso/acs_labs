package org.example.crypto_spring.controller;

import org.example.crypto_spring.entity.Exchange;
import org.example.crypto_spring.service.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeService service;

    public ExchangeController(ExchangeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("exchanges", service.getAllWithCryptos());
        return "exchanges-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("exchange", new Exchange());
        return "exchange-form";
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Exchange exchange) {
        service.save(exchange);
        return "redirect:/exchanges";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("exchange", service.getById(id));
        return "exchange-form";
    }

    @PostMapping("/edit")
    public String editSubmit(@ModelAttribute Exchange exchange) {
        service.save(exchange);
        return "redirect:/exchanges";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/exchanges";
    }
}
