package dem2k;

import com.binance.api.client.domain.market.TickerPrice;

public class TickerPriceChange {

    private Double priceChange;
    private TickerPrice oldPrice;
    private TickerPrice newPrice;

    public TickerPriceChange(TickerPrice oldPrice, TickerPrice newPrice) {
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        double pOld = Double.parseDouble(oldPrice.getPrice());
        double pNew = Double.parseDouble(newPrice.getPrice());
        priceChange = (pNew - pOld) / pOld * 100;
    }

    public Double getPriceChange() {
        return priceChange;
    }

    public Double getPriceChangeAbs() {
        return Math.abs(priceChange);
    }

    public TickerPrice getOldPrice() {
        return oldPrice;
    }

    public TickerPrice getNewPrice() {
        return newPrice;
    }

    @Override
    public String toString() {
        return "TickerPriceChange{" +
                "oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                ", priceChange=" + priceChange +
                '}';
    }
}
