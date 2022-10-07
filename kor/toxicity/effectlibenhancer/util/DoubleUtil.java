package kor.toxicity.effectlibenhancer.util;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class DoubleUtil {

    private static final DoubleUtil me;

    static {
        me = new DoubleUtil();
    }

    public static DoubleUtil getInstance() {
        return me;
    }

    public DoubleParser[] parseList(String s) {
        String[] t = (s.contains(",")) ? s.split(",") : new String[] {s};
        DoubleParser[] ret = new DoubleParser[t.length];
        IntStream.range(0,t.length).forEach(i -> ret[i] = parse(t[i]));
        return ret;
    }
    public DoubleParser parse(String s) {
        if (s.contains("to")) {
            String[] t = s.split("to");
            if (t.length < 2) return () -> 0;
            try {
                return () -> ThreadLocalRandom.current().nextDouble(Double.parseDouble(t[0]),Double.parseDouble(t[1]));
            } catch (Exception e) {
                return () -> 0;
            }
        } else {
            try {
                return () -> Double.parseDouble(s);
            } catch (Exception e) {
                return () -> 0;
            }
        }
    }
}
