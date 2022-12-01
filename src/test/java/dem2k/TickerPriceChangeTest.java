package dem2k;


import org.junit.Assert;
import org.junit.Test;

import com.binance.api.client.domain.market.TickerPrice;

public class TickerPriceChangeTest {

    @Test
    public void priceUp() {
        TickerPrice pOld = new TickerPrice();
        pOld.setSymbol("BTCUSDT");
        pOld.setPrice("40");
        TickerPrice pNew = new TickerPrice();
        pNew.setSymbol("BTCUSDT");
        pNew.setPrice("50");

        TickerPriceChange change = new TickerPriceChange(pOld, pNew);
        Assert.assertEquals(Double.valueOf(25), change.getPriceChange());
    }

    @Test
    public void priceDown() {
        TickerPrice oldpr = new TickerPrice();
        oldpr.setSymbol("BTCUSDT");
        oldpr.setPrice("50");
        TickerPrice newpr = new TickerPrice();
        newpr.setSymbol("BTCUSDT");
        newpr.setPrice("40");

        TickerPriceChange change = new TickerPriceChange(oldpr, newpr);
        Assert.assertEquals(Double.valueOf(-20), change.getPriceChange());
    }

    @Test
    public void priceNotchanged() {
        TickerPrice oldpr = new TickerPrice();
        oldpr.setSymbol("BTCUSDT");
        oldpr.setPrice("50");
        TickerPrice newpr = new TickerPrice();
        newpr.setSymbol("BTCUSDT");
        newpr.setPrice("50");

        TickerPriceChange change = new TickerPriceChange(oldpr, newpr);
        Assert.assertEquals(Double.valueOf(0), change.getPriceChange());
    }

}
