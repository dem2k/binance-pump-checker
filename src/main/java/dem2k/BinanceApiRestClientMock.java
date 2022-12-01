package dem2k;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.DepositAddress;
import com.binance.api.client.domain.account.DepositHistory;
import com.binance.api.client.domain.account.DustTransferResponse;
import com.binance.api.client.domain.account.NewOCO;
import com.binance.api.client.domain.account.NewOCOResponse;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.OrderList;
import com.binance.api.client.domain.account.SubAccountTransfer;
import com.binance.api.client.domain.account.Trade;
import com.binance.api.client.domain.account.TradeHistoryItem;
import com.binance.api.client.domain.account.WithdrawHistory;
import com.binance.api.client.domain.account.WithdrawResult;
import com.binance.api.client.domain.account.request.AllOrderListRequest;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderListRequest;
import com.binance.api.client.domain.account.request.CancelOrderListResponse;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OrderListStatusRequest;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;

public class BinanceApiRestClientMock implements BinanceApiRestClient {

    private int numofcalls = 1;

    private Map<String, TickerPrice> prise = new HashMap<>();

    public BinanceApiRestClientMock() {
        putnew("ETHUSDT", "2500");
        putnew("BTCUSDT", "30000");
        putnew("TROYBTC", "0.0000001974");
        putnew("XLMUPUSDT", "0.08281");
        putnew("XLMDOWNUSDT", "0.0002057");
        putnew("ARDRBTC", "0.00000659");
        putnew("BIFIBNB", "3.076");
        putnew("MKRBNB", "8.997");
        putnew("YFIBUSD", "32955.42");
    }

    private void putnew(String symbol, String price) {
        TickerPrice tp = new TickerPrice();
        tp.setPrice(price);
        tp.setSymbol(symbol);
        prise.put(symbol, tp);
    }

    @Override
    public List<TickerPrice> getAllPrices() {
        for (Map.Entry<String, TickerPrice> entry : prise.entrySet()) {
            TickerPrice ticker = new TickerPrice();
            ticker.setSymbol(entry.getKey());
            double pOld = Double.parseDouble(entry.getValue().getPrice());
            double pCng = Math.sin(numofcalls + pOld) * 3;
            double pNew = pOld + (pOld / 100 * pCng);
            ticker.setPrice(Utils.toStringTrunc(pNew));
            prise.put(entry.getKey(), ticker);
        }
        numofcalls++;
        return prise.values().stream().collect(Collectors.toList());
    }

    @Override
    public TickerPrice getPrice(String symbol) {
        return prise.get(symbol);
    }

    @Override
    public void ping() {

    }

    @Override
    public Long getServerTime() {
        return null;
    }

    @Override
    public ExchangeInfo getExchangeInfo() {
        return null;
    }

    @Override
    public List<Asset> getAllAssets() {
        return null;
    }

    @Override
    public OrderBook getOrderBook(String symbol, Integer limit) {
        return null;
    }

    @Override
    public List<TradeHistoryItem> getTrades(String s, Integer integer) {
        return null;
    }

    @Override
    public List<TradeHistoryItem> getHistoricalTrades(String s, Integer integer, Long aLong) {
        return null;
    }

    @Override
    public List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime) {
        return null;
    }

    @Override
    public List<AggTrade> getAggTrades(String symbol) {
        return null;
    }

    @Override
    public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
        return null;
    }

    @Override
    public List<Candlestick> getCandlestickBars(String s, CandlestickInterval candlestickInterval, Integer integer) {
        return null;
    }

    @Override
    public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval) {
        return null;
    }

    @Override
    public TickerStatistics get24HrPriceStatistics(String symbol) {
        return null;
    }

    @Override
    public List<TickerStatistics> getAll24HrPriceStatistics() {
        return null;
    }


    @Override
    public List<BookTicker> getBookTickers() {
        return null;
    }

    @Override
    public NewOrderResponse newOrder(NewOrder order) {
        return null;
    }

    @Override
    public void newOrderTest(NewOrder order) {

    }

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
        return null;
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
        return null;
    }

    @Override
    public List<Order> getOpenOrders(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public List<Order> getAllOrders(AllOrdersRequest orderRequest) {
        return null;
    }

    @Override
    public NewOCOResponse newOCO(NewOCO newOCO) {
        return null;
    }

    @Override
    public CancelOrderListResponse cancelOrderList(CancelOrderListRequest cancelOrderListRequest) {
        return null;
    }

    @Override
    public OrderList getOrderListStatus(OrderListStatusRequest orderListStatusRequest) {
        return null;
    }

    @Override
    public List<OrderList> getAllOrderList(AllOrderListRequest allOrderListRequest) {
        return null;
    }

    @Override
    public Account getAccount(Long recvWindow, Long timestamp) {
        return null;
    }

    @Override
    public Account getAccount() {
        return null;
    }

    @Override
    public List<Trade> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp) {
        return null;
    }

    @Override
    public List<Trade> getMyTrades(String symbol, Integer limit) {
        return null;
    }

    @Override
    public List<Trade> getMyTrades(String symbol) {
        return null;
    }

    @Override
    public List<Trade> getMyTrades(String s, Long aLong) {
        return null;
    }

    @Override
    public WithdrawResult withdraw(String s, String s1, String s2, String s3, String s4) {
        return null;
    }

    @Override
    public DustTransferResponse dustTranfer(List<String> list) {
        return null;
    }

    @Override
    public DepositHistory getDepositHistory(String asset) {
        return null;
    }

    @Override
    public WithdrawHistory getWithdrawHistory(String asset) {
        return null;
    }

    @Override
    public List<SubAccountTransfer> getSubAccountTransfers() {
        return null;
    }

    @Override
    public DepositAddress getDepositAddress(String asset) {
        return null;
    }

    @Override
    public String startUserDataStream() {
        return null;
    }

    @Override
    public void keepAliveUserDataStream(String listenKey) {

    }

    @Override
    public void closeUserDataStream(String listenKey) {

    }
}
