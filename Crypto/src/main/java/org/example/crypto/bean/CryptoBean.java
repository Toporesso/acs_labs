package org.example.crypto.bean;

import org.example.crypto.model.CryptoCoin;
import org.example.crypto.model.Exchange;
import org.example.crypto.service.CryptoCoinService;
import org.example.crypto.service.ExchangeService;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

@Named
@ViewScoped
public class CryptoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Long> selectedExchangeIds = new ArrayList<>();

    @EJB
    private CryptoCoinService cryptoService;
    @EJB
    private ExchangeService exchangeService;

    private CryptoCoin crypto = new CryptoCoin();
    private CryptoCoin selectedCrypto;


    public String addCrypto() {
        if (crypto.getName() == null || crypto.getName().trim().isEmpty() ||
                crypto.getSymbol() == null || crypto.getSymbol().trim().isEmpty() ||
                crypto.getPriceUsd() == null || crypto.getMarketCap() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Все поля обязательны для заполнения!", null));
            return null;
        }

        cryptoService.add(crypto);

        if (crypto.getExchanges() == null) {
            crypto.setExchanges(new HashSet<>());
        }

        if (selectedExchangeIds != null && !selectedExchangeIds.isEmpty()) {
            for (Long exchangeId : selectedExchangeIds) {
                Exchange ex = exchangeService.getById(exchangeId);
                if (ex != null) {
                    ex.getCryptos().add(crypto);
                    crypto.getExchanges().add(ex);
                    exchangeService.update(ex);
                }
            }
        }

        cryptoService.update(crypto);

        crypto = new CryptoCoin();
        selectedExchangeIds = new ArrayList<>();

        return "index?faces-redirect=true";
    }

    public String editCrypto(CryptoCoin coin) {
        this.selectedCrypto = coin;
        if (coin != null && coin.getExchanges() != null) {
            selectedExchangeIds.clear();
            for (Exchange ex : coin.getExchanges()) {
                selectedExchangeIds.add(ex.getId());
            }
        } else {
            selectedExchangeIds.clear();
        }
        return null;
    }

    public String updateCrypto() {
        if (selectedCrypto != null) {
            if (selectedCrypto.getExchanges() == null) {
                selectedCrypto.setExchanges(new HashSet<>());
            } else {
                selectedCrypto.getExchanges().clear();
            }

            for (Exchange ex : exchangeService.getAll()) {
                if (ex.getCryptos().contains(selectedCrypto)) {
                    ex.getCryptos().remove(selectedCrypto);
                    exchangeService.update(ex);
                }
            }

            if (selectedExchangeIds != null) {
                for (Long exchangeId : selectedExchangeIds) {
                    Exchange ex = exchangeService.getById(exchangeId);
                    if (ex != null) {
                        ex.getCryptos().add(selectedCrypto);
                        selectedCrypto.getExchanges().add(ex);
                        exchangeService.update(ex);
                    }
                }
            }

            cryptoService.update(selectedCrypto);
            selectedCrypto = null;
            selectedExchangeIds = new ArrayList<>();
        }
        return "index?faces-redirect=true";
    }

    public String deleteCrypto(CryptoCoin coin) {
        if (coin != null) {
            cryptoService.delete(coin.getId());
        }
        return null;
    }

    public CryptoCoin getCrypto() { return crypto; }
    public void setCrypto(CryptoCoin crypto) { this.crypto = crypto; }

    public CryptoCoin getSelectedCrypto() { return selectedCrypto; }
    public void setSelectedCrypto(CryptoCoin selectedCrypto) { this.selectedCrypto = selectedCrypto; }

    public List<Long> getSelectedExchangeIds() { return selectedExchangeIds; }
    public void setSelectedExchangeIds(List<Long> selectedExchangeIds) { this.selectedExchangeIds = selectedExchangeIds; }

    public List<CryptoCoin> getCryptoList() { return cryptoService.getAll(); }
    public List<Exchange> getAllExchanges() { return exchangeService.getAll(); }
}
