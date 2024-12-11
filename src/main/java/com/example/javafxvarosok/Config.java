package com.example.javafxvarosok;

import com.oanda.v20.account.AccountID;

public class Config {
    private Config() {}

    // Az API alap URL-je
    public static final String URL = "https://api-fxpractice.oanda.com";

    // Az API token
    public static final String TOKEN = "fcd9d50c2832d73cee90e55aeaed0849-5f750ec1d1393fdd6821f97cc469f3b0";

    // A számla azonosító
    public static final AccountID ACCOUNTID = new AccountID("101-004-30534366-001");

    // Metódus az URL eléréséhez
    public static String getApiUrl() {
        return URL;
    }

    // Metódus az API token eléréséhez
    public static String getApiKey() {
        return TOKEN;
    }
}
