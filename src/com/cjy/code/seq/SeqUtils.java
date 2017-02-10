package com.cjy.code.seq;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Created by shylock on 16-11-15.
 */
class SeqUtils {
    private static long   nextLong         = 0;
    private static final String SEQ_FORMAT       = "FO{0}{1}";

    private static final String DATE_TIME_FORMAT = "yyyyMMDDHHmmSSsss";

    static String random() {
        final String now = new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
        String randomNum = randomNum(11);
        return MessageFormat.format(SEQ_FORMAT, now, randomNum);
    }

    public static void main(String[] args) {
        int i = 0;
        while (i++ < 100000) {
            String s = random();

            System.out.println("s :" + s);
        }

    }

    private static synchronized String randomNum(int length) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(length);
        return nf.format(nextLong++).replace(",", "");
    }

}