package com.util.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestClient {

    private Client client = null;

    private RestClient(){
        this.client = Client.create();
    }

    public static RestClient getInstance(){
        return InnerRestClient.REST_CLIENT_HOLDER;
    }

    private static class InnerRestClient{
        private static RestClient REST_CLIENT_HOLDER = new RestClient();
    }

    public static String getApiResult(String url){
        WebResource webResource = getInstance().client.resource(url);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        ClientResponse clientResponse =
                webResource
                        .accept("application/json")
                        .get(ClientResponse.class);

        String result = clientResponse.getEntity(String.class);
//        SlackAuthVo result = clientResponse.getEntity(SlackAuthVo.class);
        return result;
    }

    public Client getClient() {
        return client;
    }
}
