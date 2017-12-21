package com.kzrbill.gocd.plugin.notifications.webhooks.requests;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class ApiRequestProxy implements ApiRequest {

    private HttpRequestWithBody unirest;

    public ApiRequest post(String apiUrl) {
        this.unirest = Unirest.post(apiUrl);
        return this;
    }

    public ApiRequest header(String name, String value) {
        this.unirest = this.unirest.header(name, value);
        return this;
    }

    public RequestBody body(String stageStatusJson) {
        return new RequestBodyProxy(this.unirest.body(stageStatusJson));
    }
}


