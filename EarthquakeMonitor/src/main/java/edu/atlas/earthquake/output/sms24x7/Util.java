package edu.atlas.earthquake.output.sms24x7;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Util {
    private static final String UTF_8 = "utf-8";

    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, UTF_8);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
}