package dem2k;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;

public class PriceChangeService {

    private BinanceApiRestClient client;
    private Map<String, TickerPrice> oldPrices = new HashMap<>();

    public PriceChangeService(BinanceApiRestClient client) {
        this.client = client;
    }

    public List<TickerPrice> getAllPrices() {
        return client.getAllPrices();
    }

    public TickerPrice getTickerPrice(String symbol) {
        return client.getPrice(symbol);
    }

    public List<TickerPriceChange> getPriceChanges() {
        List<TickerPrice> newPrices = client.getAllPrices();
        List<TickerPriceChange> priceChanges = newPrices.stream()
                .map(this::updatePrice)
                .collect(Collectors.toList());
        return priceChanges;
    }

    private TickerPriceChange updatePrice(TickerPrice pNew) {
        TickerPrice pOld = oldPrices.getOrDefault(pNew.getSymbol(), pNew);
        oldPrices.put(pNew.getSymbol(), pNew);
        return new TickerPriceChange(pOld, pNew);
    }

}
