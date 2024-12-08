/*package com.example.javafxvarosok;

import soapclient.MNBArfolyamServiceSoap;
import soapclient.MNBArfolyamServiceSoapImpl;

public class MNBSoapClient {
    private MNBArfolyamServiceSoap service;

    public MNBSoapClient() {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        service = impl.getCustomBindingMNBArfolyamServiceSoap();
    }

    // Összes adat lekérdezése
    public String getAllExchangeRates() {
        try {
            return service.getCurrentExchangeRates();
        } catch (Exception e) {
            e.printStackTrace();
            return "Hiba történt az árfolyamok lekérdezésekor.";
        }
    }

    // Adatok lekérdezése időtartam és valuta alapján
    public String getExchangeRates(String startDate, String endDate, String currency) {
        try {
            return service.getExchangeRates(startDate, endDate, currency);
        } catch (Exception e) {
            e.printStackTrace();
            return "Hiba történt az árfolyamok lekérdezésekor.";
        }
    }
}
*/