package com.share.data.util.restclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 참고자료
 * https://examples.javacodegeeks.com/core-java/json/java-json-parser-example/
 * https://zzznara2.tistory.com/673
 */
public class ApacheHttpClient {

    private CloseableHttpClient httpClient = null;

    private ApacheHttpClient(){
        this.httpClient = HttpClients.createDefault();
    }

    public static ApacheHttpClient getInstance(){
        return InnerApacheHttpClient.HTTP_CLIENT;
    }

    private static class InnerApacheHttpClient{
        private static final ApacheHttpClient HTTP_CLIENT = new ApacheHttpClient();
    }

    public static String getApiResult(String url) throws IOException {
        CloseableHttpClient client = getInstance().getHttpClient();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("User-Agent", "Mozila/5.0");

        CloseableHttpResponse httpResponse = client.execute(httpGet);

        System.out.println("GET Response Status");
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        String json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

        System.out.println("json >>> " + json);

        return json;

    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}
