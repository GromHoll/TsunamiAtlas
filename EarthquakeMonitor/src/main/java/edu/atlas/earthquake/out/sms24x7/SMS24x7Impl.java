package edu.atlas.earthquake.out.sms24x7;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import java.io.IOException;

import static edu.atlas.earthquake.out.sms24x7.Util.encode;

public class SMS24x7Impl {
    private static final String API_URL = "http://api.sms24x7.ru/";

    private final HttpClient HTTP_CLIENT;

    {
        final PoolingClientConnectionManager ccm = new PoolingClientConnectionManager();
        ccm.setMaxTotal(1);
        HTTP_CLIENT = new DefaultHttpClient(ccm);
    }

    public String send(String email, String password, String from, String to, String text) throws IOException {
        final HttpGet get;
        final String result;

        email = encode(email);
        password = encode(password);
        text = encode(text);
        to = encode(to);
        from = encode(from);
        get = new HttpGet(API_URL + "?method=push_msg&email=" + email + "&password=" + password + "&text=" + text + "&phone=" + to + "&sender_name=" + from);
        result = HTTP_CLIENT.execute(get, new BasicResponseHandler());
        return result;
    }

}