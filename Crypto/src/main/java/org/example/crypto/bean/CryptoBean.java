package org.example.crypto.bean;

import org.example.crypto.model.CryptoCoin;
import org.example.crypto.service.CryptoCoinService;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CryptoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CryptoCoinService cryptoService;

    private CryptoCoin crypto = new CryptoCoin();
    private CryptoCoin selectedCrypto; // для редактирования/удаления

    // Получение списка всех криптовалют
    public List<CryptoCoin> getCryptoList() {
        return cryptoService.getAll();
    }

    // Добавление новой криптовалюты
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
        crypto = new CryptoCoin(); // сброс формы
        return null;
    }

    // Выбор крипты для редактирования
    public String editCrypto(CryptoCoin coin) {
        this.selectedCrypto = coin;
        return null;
    }

    // Обновление выбранной крипты
    public String updateCrypto() {
        if (selectedCrypto != null) {
            cryptoService.update(selectedCrypto);
            selectedCrypto = null;
        }
        return null;
    }

    // Удаление криптовалюты
    public String deleteCrypto(CryptoCoin coin) {
        if (coin != null) {
            cryptoService.delete(coin.getId());
        }
        return null;
    }

    // Геттеры и сеттеры
    public CryptoCoin getCrypto() { return crypto; }
    public void setCrypto(CryptoCoin crypto) { this.crypto = crypto; }

    public CryptoCoin getSelectedCrypto() { return selectedCrypto; }
    public void setSelectedCrypto(CryptoCoin selectedCrypto) { this.selectedCrypto = selectedCrypto; }
}
