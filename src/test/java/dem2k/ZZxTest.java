package dem2k;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author DKL on 20.07.2021.
 */
public class ZZxTest {

    @Test
    @Ignore
    public void name() {
        double pold = Double.parseDouble("15560.00");
        double pNew = Double.parseDouble("15559.00");
        // 7347.60000000   7349.60000000   0.02721977244270238
        //  15560.00        15559.00        -0.006426735218508997
        double abs = Math.abs(pold - pNew);
        System.out.println(abs);
    }

    @Test
    @Ignore
    public void format() {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        twoDForm.setDecimalFormatSymbols(dfs);
        double num = Double.parseDouble("-3.216846783153219");
        System.out.println(twoDForm.format(num));
    }

    @Test
    @Ignore
    public void price24() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiRestClient client = factory.newRestClient();
        String symbol = "BTCUSDT";
        TickerStatistics tickerStatistics = client.get24HrPriceStatistics(symbol);
        System.out.println(tickerStatistics);
        System.out.println("-----------------------");
        System.out.println(client.getPrice(symbol));
        // volume=115567.23719700
        // volume=115286.79190500,
        // volume=115262.11652600,
        // volume=115248.82445500,
        // volume=115404.16076100,
    }

    
    @Test
    @Ignore
    public void progress() throws InterruptedException {
        int seconds = 60;
        ProgressBar pb = new ProgressBar("Test", seconds);
        pb.setExtraMessage("Reading...");
        while (seconds > 0) {
            pb.step();
            Thread.sleep(1000);
        }
    }

    @Test
    @Ignore
    public void alltickers() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiRestClient client = factory.newRestClient();
        // Getting all latest prices
        List<TickerPrice> allPrices = client.getAllPrices();
        for (TickerPrice ticker : allPrices) {
            System.out.println(ticker.getSymbol());
        }
    }


    public static void main(String[] args) throws Exception {
        List<String> includes =
                Arrays.stream("ETHUSDT,XXXYYY".split(",")).collect(Collectors.toList());
        BinancePumpChecker checker = new BinancePumpChecker(new BinanceApiRestClientMock());
        checker.setTimePeriod(5);
        checker.setShowLink(false);
        checker.setShowProgressBar(true);
        checker.setAllPairs(false);
        checker.setInclude(includes);
        checker.call();
    }

    @Test
    @Ignore
    public void testkooeficient() {
        TickerPrice price = new TickerPrice();
        price.setSymbol("BTCUSDT");
        price.setPrice("30000");
        for (int i = 0; i < 100; i++) {
            Double act = Double.parseDouble(price.getPrice());
            act+=Math.sin(i)*100;
            price.setPrice(act.toString());
            System.out.println(price);
        }
    }

    @Test
    @Ignore
    public void progrssImmidiat() throws InterruptedException {
        ProgressBar pb = new ProgressBarBuilder()
                .setInitialMax(11)
                .setTaskName("Waiting").setStyle(ProgressBarStyle.ASCII).build();
        Thread.sleep(10);
        
    }
}
