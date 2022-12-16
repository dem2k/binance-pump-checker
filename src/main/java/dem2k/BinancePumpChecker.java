package dem2k;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import picocli.CommandLine;

@CommandLine.Command(name = "binance-pump-checker", version = BinancePumpChecker.VERSION, mixinStandardHelpOptions = true,
        description = "Observes price changes on Binance.", showDefaultValues = false)
public class BinancePumpChecker implements Callable<Integer> {

    public final static String VERSION = "0.5.6";

    private final DecimalFormat fmt2digit;

    @CommandLine.Option(names = {"-t"}, description = "Update period. Default: 60sec.")
    private long timePeriod = 60;

    @CommandLine.Option(names = {"-p"}, description = "Price change in percent. Default: 1")
    private double threshold = 1.0;

    @CommandLine.Option(names = {"-all"}, description = "All pairs or USDT only.")
    private boolean allPairs = false;

    @CommandLine.Option(names = {"-pb"}, description = "Show progress bar while waiting.")
    private boolean showPb = false;

    @CommandLine.Option(names = {"-updown"}, description = "Pairs with UP-and-DOWN/USDT (leverage tokens).")
    private boolean updown = false;

    @CommandLine.Option(names = {"-link"}, description = "Show link for Coin on binance.")
    private boolean showLink = false;

    @CommandLine.Option(names = {"-incl"}, split = ",", description = "Only shows these coins. Comma separated.")
    private List<String> include = new ArrayList<>();

    @CommandLine.Option(names = {"-dryrun"}, description = "For debug only. Run service without connect to Binance.")
    private boolean dryrun = false;

    @CommandLine.Option(names = {"-nots"}, description = "Do not show Timestamp.")
    private boolean nots = false;

    @CommandLine.Option(names = {"-type"}, description = "Check only for Pump or Dump. Default both.")
    private PumpType type = null;

    private PriceChangeService service;

    public static void main(String[] args) {
        BinanceApiRestClient client =
                BinanceApiClientFactory.newInstance().newRestClient();

        int exitCode = new CommandLine(new BinancePumpChecker(client))
                .setCaseInsensitiveEnumValuesAllowed(true)
                .execute(args);
        System.exit(exitCode);
    }

    public BinancePumpChecker(BinanceApiRestClient client) {
        this.service = new PriceChangeService(client);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        fmt2digit = new DecimalFormat("0.00");
        fmt2digit.setDecimalFormatSymbols(dfs);
    }

    @Override
    public Integer call() throws Exception {
        if (dryrun) { // das ist nicht nach solid, mock muss schon von aussen injected werden... wie?
            this.service = new PriceChangeService(new BinanceApiRestClientMock());
        }

        include = Utils.listToUpperCase(include);

        System.out.printf("%n*** Pump-Checker by OldSwede / v%s / Greetings to all CryptoFLUD members *** %n", VERSION);
        System.out.printf("%s %n", dryrun ? "***   Dryrun Mode . Dryrun Mode . Dryrun Mode . Dryrun Mode . Dryrun Mode   ***" : "");
        System.out.printf("       Ticker      Change (%s%%)       Old Price            New Price      %n", threshold);
        System.out.println("=============================================================================");

        long step = 0; // dummy value for endless loop.
        while (step++ >= 0) {
            this.printChanges();
            this.syncPeriod();
        }
        return 0;
    }


    private void syncPeriod() throws Exception {
        Thread.sleep(3000); // less then 5 sec isnt allowed.
        ProgressBar pb = showPb ? new ProgressBarBuilder()
                .setInitialMax(timePeriod)
                .setTaskName("Waiting").setStyle(ProgressBarStyle.ASCII).build() : null;
        for (int i = 0; i <= timePeriod; i++) {
            Thread.sleep(1000);
            if (isPeriodSynced()) {
                break;
            }
            if (showPb) {
                pb.step();
            }
        }
        if (showPb) {
            pb.stepTo(timePeriod);
            pb.close();
        }
    }

    private boolean isPeriodSynced() {
        LocalTime now = LocalTime.now().withNano(0);
        // seconds from hour beginn.
        int h0ss = now.getMinute() * 60 + now.getSecond();
        return (h0ss % timePeriod == 0);
    }


    private void printChanges() {
        List<TickerPriceChange> changed = service.getPriceChanges().stream()
                .filter(this::isSelected)
                .filter(this::isThresholded)
                .sorted(Comparator.comparing(TickerPriceChange::getPriceChangeAbs))
                .collect(Collectors.toList());
        if (!nots && changed.size() > 0) {
            System.out.printf("--- %s ----------------------------------------------------------------%n",
                    LocalTime.now().withNano(0).format(DateTimeFormatter.ISO_TIME));
        }
        for (TickerPriceChange change : changed) {
            System.out.printf("%16s %10s %20s %20s %n",
                    change.getOldPrice().getSymbol(), fmt2digit.format(change.getPriceChange()),
                    change.getOldPrice().getPrice(), change.getNewPrice().getPrice());
            if (showLink) {
                System.out.println("                        https://www.binance.com/en/trade/" + change.getOldPrice().getSymbol());
            }
        }
    }

    private boolean isThresholded(TickerPriceChange change) {
        return Math.abs(change.getPriceChange()) >= threshold;
    }

    private boolean isSelected(TickerPriceChange change) {
        if (!include.isEmpty()) {
            return include.contains(change.getOldPrice().getSymbol());
        }

        if (PumpType.PUMP.equals(type) && change.getPriceChange() < 0) {
            return false;
        }

        if (PumpType.DUMP.equals(type) && change.getPriceChange() > 0) {
            return false;
        }

        boolean isupdown =
                change.getOldPrice().getSymbol().endsWith("UPUSDT") || change.getOldPrice().getSymbol().endsWith("DOWNUSDT");
        if (updown && isupdown) {
            return true;
        }

        boolean isusdt =
                !isupdown && change.getOldPrice().getSymbol().endsWith("USDT");

        return allPairs || isusdt;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setAllPairs(boolean allPairs) {
        this.allPairs = allPairs;
    }

    public void setUpdown(boolean updown) {
        this.updown = updown;
    }

    public void setShowProgressBar(boolean showPb) {
        this.showPb = showPb;
    }

    public void setShowLink(boolean showLink) {
        this.showLink = showLink;
    }

    public void setInclude(List<String> include) {
        this.include = include;
    }
}
