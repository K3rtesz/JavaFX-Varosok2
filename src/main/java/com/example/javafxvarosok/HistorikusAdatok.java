package com.example.javafxvarosok;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;

import java.util.List;

import static com.oanda.v20.instrument.CandlestickGranularity.H1;

public class HistorikusAdatok {
    public static List<Candlestick> fetchHistoricalData(String instrument, String start, String end) {
        Context ctx = new ContextBuilder(Config.getApiUrl())
                .setToken(Config.getApiKey())
                .setApplication("HistorikusAdatok")
                .build();

        try {
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(instrument));
            request.setGranularity(H1); // Óránkénti adatok
            request.setFrom(start);
            request.setTo(end);

            InstrumentCandlesResponse response = ctx.instrument.candles(request);
            return response.getCandles();
        } catch (Exception e) {
            throw new RuntimeException("Hiba az API hívás során: " + e.getMessage(), e);
        }
    }
}
