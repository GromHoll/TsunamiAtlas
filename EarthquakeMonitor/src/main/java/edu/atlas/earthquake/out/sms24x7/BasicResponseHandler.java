package edu.atlas.earthquake.out.sms24x7;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class BasicResponseHandler implements ResponseHandler<String> {
    private final String encoding;

    public BasicResponseHandler() {
        this.encoding = "utf-8";
    }

    public BasicResponseHandler(String encoding) {
        this.encoding = encoding;
    }

    public String handleResponse(final HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        return entity == null ? null : EntityUtils.toString(entity, encoding);
    }

}
