# binance-pump-checker

Check Price Changes on Binance Crypto Exchange.

# installation

````
scoop bucket add dem2k https://github.com/dem2k/scoop-bucket
scoop install binance-pump-checker
````

# usage

````
Usage: binance-pump-checker [-hV] [-all] [-dryrun] [-link] [-nots] [-pb]
                            [-updown] [-p=<threshold>] [-t=<timePeriod>]
                            [-type=<type>] [-incl=<include>[,<include>...]]...
Observes price changes on Binance.
      -all           All pairs or USDT only.
      -dryrun        For debug only. Run service without connect to Binance.
  -h, --help         Show this help message and exit.
      -incl=<include>[,<include>...]
                     Only shows these coins. Comma separated.
      -link          Show link for Coin on binance.
      -nots          Do not show Timestamp.
  -p=<threshold>     Price change in percent. Default: 1
      -pb            Show progress bar while waiting.
  -t=<timePeriod>    Update period. Default: 60sec.
      -type=<type>   Check only for Pump or Dump. Default both.
      -updown        Pairs with UP-and-DOWN/USDT (leverage tokens).
  -V, --version      Print version information and exit.
````
