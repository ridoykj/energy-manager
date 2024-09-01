package com.itbd.energymanager.services.reports.tools;

import java.math.BigDecimal;

public class NumberConverter {

    public static String numberToWords(BigDecimal n) {
        return numberToWords(n.longValue());
    }

    public static String numberToWords(long n) {
        long limit = 1000000000000L, curr_hun, t = 0;

        // If zero return zero
        if (n == 0)
            return ("Zero");

        // Array to store the powers of 10
        String[] multiplier = {"", "Trillion", "Billion",
                "Million", "Thousand"};

        // Array to store numbers till 20
        String[] first_twenty = {
                "", "One", "Two", "Three",
                "Four", "Five", "Six", "Seven",
                "Eight", "Nine", "Ten", "Eleven",
                "Twelve", "Thirteen", "Fourteen", "Fifteen",
                "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };

        // Array to store multiples of ten
        String[] tens = {"", "Twenty", "Thirty",
                "Forty", "Fifty", "Sixty",
                "Seventy", "Eighty", "Ninety"};

        // If the number is less than 20, return without any
        // further computation
        if (n < 20L)
            return (first_twenty[(int) n]);
        StringBuilder answer = new StringBuilder();
        for (long i = n; i > 0; i %= limit, limit /= 1000) {
            // Store the value in multiplier[t], i.e., n =
            // 1000000, then r = 1, for multiplier(million),
            // 0 for multipliers(trillion and billion)
            // multiplier here refers to the current
            // accessible limit
            curr_hun = i / limit;
            // It might be possible that the current
            // multiplier is bigger than your number
            while (curr_hun == 0) {

                // Set i as the remainder obtained when n
                // was divided by the limit
                i %= limit;

                // Divide the limit by 1000, shifts the
                // multiplier
                limit /= 1000;

                // Get the current value in hundreds, as
                // an English system works in hundreds
                curr_hun = i / limit;

                // Shift the multiplier
                ++t;
            }

            // If the current hundred is greater than 99, Add
            // the hundreds' place
            if (curr_hun > 99)
                answer.append(first_twenty[(int) curr_hun / 100]).append(" Hundred ");

            // Bring the current hundred to tens
            curr_hun = curr_hun % 100;

            // If the value in tens belongs to [1,19], add
            // using the first_twenty
            if (curr_hun > 0 && curr_hun < 20)
                answer.append(first_twenty[(int) curr_hun]).append(" ");

                // If curr_hun is now a multiple of 10, but not
                // 0 Add the tens' value using the ten arrays
            else if (curr_hun % 10 == 0 && curr_hun != 0)
                answer.append(tens[(int) curr_hun / 10 - 1]).append(" ");

                // If the value belongs to [21,99], excluding
                // the multiples of 10, Get the ten's place and
                // one's place, and print using the first_twenty
                // array
            else if (curr_hun > 20 && curr_hun < 100)
                answer.append(tens[(int) curr_hun / 10 - 1]).append(" ").append(first_twenty[(int) curr_hun % 10]).append(" ");

            // If Multiplier has not become less than 1000,
            // shift it
            if (t < 4)
                answer.append(multiplier[(int) ++t]).append(" ");
        }
        return (answer.toString());
    }
}
