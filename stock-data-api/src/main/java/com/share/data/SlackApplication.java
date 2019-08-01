package com.share.data;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SlackApplication {
    public static final String CLIENT_ID = "680595488112.691875062899";

    public static void main(String [] args){
        SlackApplication.getSlackUserResource();
    }

    public static String getSlackUserResource(){
        String BASE_URL = "https://slack.com/oauth/authorize";
        String clientId = "680595488112.691875062899";
        String scope = "identity.basic";

        SlackApplication s = new SlackApplication();

        StringBuffer sbUrl = new StringBuffer();
        sbUrl.append(BASE_URL)
                .append("?")
                .append("scope=").append(scope)
                .append("&")
                .append("client_id=").append(clientId);

        final String FULL_URL = sbUrl.toString();

        return getApiResult(FULL_URL);
    }

    public static String getApiResult(String requestUrl){
        String apiResult = null;

        Client client = Client.create();

        WebResource webResource = client.resource(requestUrl);

        ClientResponse response =
                webResource
                        .accept("application/json")
                        .get(ClientResponse.class);

        apiResult = response.getEntity(String.class);

        System.out.println("apiResult :: \n" + apiResult);
        return apiResult;
    }
}
