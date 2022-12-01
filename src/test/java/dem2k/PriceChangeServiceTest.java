package dem2k;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;

public class PriceChangeServiceTest {

    private BinanceApiRestClient client = new BinanceApiRestClientMock();
    private PriceChangeService service = new PriceChangeService(client);

    @Test
    public void checkfirstprice() {
        TickerPrice btcusdt = service.getTickerPrice("BTCUSDT");
        Assert.assertEquals("30000", btcusdt.getPrice());
    }

    @Test
    public void checknumcalls() {
        List<TickerPrice> call1 = service.getAllPrices();
        Assert.assertTrue(call1.size()>0);
    }

    @Test
    public void pricechange() {
        List<TickerPriceChange> call1 = service.getPriceChanges();
        Assert.assertEquals(Double.valueOf(0), call1.get(0).getPriceChange());
        List<TickerPriceChange> call2 = service.getPriceChanges();
        Assert.assertTrue(call2.get(0).getPriceChange() != 0);
    }
}

