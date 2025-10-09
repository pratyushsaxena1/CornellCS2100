package cs2110;

import java.util.Random;

/**
 * Contains methods for computing the optimal achievable profit of a stock transaction based on its
 * price history in a given time window.
 */
public class Trading {

    /**
     * Represents a stock transaction in which a share is purchased at the `purchaseTime` and sold
     * at the `sellTime`. Requires that `purchaseTime < sellTime`.
     */
    record BuySellTransaction(int purchaseTime, int sellTime) {

    }

    /**
     * Returns the profit earned through the given `BuySellTransaction t` for the given `prices`
     * array.
     */
    static int profit(int[] prices, BuySellTransaction t) {
        return prices[t.sellTime()] - prices[t.purchaseTime];
    }

    /**
     * Returns the *index* of the maximum value in `prices(i..]`. Requires that `0 <= i <
     * prices.length-1`.
     */
    static int argmaxTail(int[] prices, int i) {
        int loopTracker = i + 1;
        int currMaxVal = prices[i + 1];
        int indexOfCurrMax = i + 1;
        /*
        Loop invariant: currMaxVal is the max value found from prices(i..loopTracker] and the
        indexOfCurrMax is set to the index of this value.
         */
        while (loopTracker < prices.length) {
            if (currMaxVal < prices[loopTracker]) {
                currMaxVal = prices[loopTracker];
                indexOfCurrMax = loopTracker;
            }
            loopTracker += 1;
        }
        return indexOfCurrMax;
    }

    /**
     * Returns a BuySellTransaction with the maximum achievable profit for the given `prices`
     * window.
     */
    static BuySellTransaction optimalTransaction1(int[] prices) {
        BuySellTransaction opt = null;
        int i = 0;
        /*
         * Loop invariant: opt references a `Transaction` among all those with `purchaseTime` in
         * `[0..i)` with the maximum achievable profit.
         */
        while (i < prices.length - 1) {
            int sellIndex = argmaxTail(prices, i);
            BuySellTransaction currentTransaction = new BuySellTransaction(i, sellIndex);
            if (opt == null || profit(prices, currentTransaction) > profit(prices, opt)) {
                opt = currentTransaction;
            }
            i++;
        }
        return opt;
    }

    /**
     * Returns a BuySellTransaction with the maximum achievable profit for the given `prices`
     * window.
     */
    static BuySellTransaction optimalTransaction2(int[] prices) {
        if (prices == null || prices.length < 2) {
            return null;
        }
        int minPriceIndex = 0;
        BuySellTransaction opt = new BuySellTransaction(0, 1);
        /*
        Loop invariant: minPriceIndex is the index of the lowest price found from prices[0..i) and
        opt is the best transaction found so far when the buy day and sell day are in prices[0..i).
         */
        for (int i = 1; i < prices.length; i++) {
            int currentProfit = prices[i] - prices[minPriceIndex];
            if (opt == null || currentProfit > profit(prices, opt)) {
                opt = new BuySellTransaction(minPriceIndex, i);
            }
            if (prices[i] < prices[minPriceIndex]) {
                minPriceIndex = i;
            }
        }
        return opt;
    }

    public static void main(String[] args) {
        int[] arraySizes = {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000,
                1000000};
        Random rand = new Random();
        for (int sizeIndex = 0; sizeIndex < arraySizes.length; sizeIndex++) {
            int[] prices = new int[arraySizes[sizeIndex]];
            prices[0] = 100;
            for (int i = 1; i < arraySizes[sizeIndex]; i++) {
                prices[i] = prices[i - 1] + rand.nextInt(11) - 5;
                if (prices[i] < 1) {
                    prices[i] = 1;
                }
            }
            System.out.println("Array size = " + arraySizes[sizeIndex]);
            double startTime = System.nanoTime();
            optimalTransaction1(prices);
            double endTime = System.nanoTime();
            double elapsedTimeMs = (endTime - startTime) / 1000000;
            System.out.println("  optimalTransaction1: " + elapsedTimeMs + " ms");
            startTime = System.nanoTime();
            optimalTransaction2(prices);
            endTime = System.nanoTime();
            elapsedTimeMs = (endTime - startTime) / 1000000;
            System.out.println("  optimalTransaction2: " + elapsedTimeMs + " ms");
            System.out.println();
        }
    }
}