package org.ei.enketoonwebview.agent;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.InputStream;

public class HttpAgent {
    private final DefaultHttpClient httpClient;

    public HttpAgent() {
        httpClient = new DefaultHttpClient();
    }

    public InputStream fetch(String url) {
        HttpPost httpPost = new HttpPost("http://enketo.formhub.org/transform/get_html_form");
        try {
            httpPost.setEntity(new StringEntity("server_url=" + url, HTTP.UTF_8));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getEntity() != null) {
                return response.getEntity().getContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
