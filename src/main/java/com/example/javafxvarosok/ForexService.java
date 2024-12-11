package com.example.javafxvarosok;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ForexService {
    public static String sendApiRequest(String endpoint) throws Exception {
        URL url = new URL(Config.getApiUrl() + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + Config.getApiKey());

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        return response.toString();
    }
}
