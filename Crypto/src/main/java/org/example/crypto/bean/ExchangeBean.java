package org.example.crypto.bean;

import org.example.crypto.model.Exchange;
import org.example.crypto.service.ExchangeService;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ExchangeBean implements Serializable {

    @EJB
    private ExchangeService exchangeService;

    private Exchange exchange = new Exchange();

    public List<Exchange> getExchangeList() {
        return exchangeService.getAll();
    }

    public String addExchange() {
        exchangeService.add(exchange);
        exchange = new Exchange();
        return null;
    }

    public String deleteExchange(Exchange ex) {
        exchangeService.delete(ex.getId());
        return null;
    }

    public Exchange getExchange() { return exchange; }
    public void setExchange(Exchange exchange) { this.exchange = exchange; }
}
