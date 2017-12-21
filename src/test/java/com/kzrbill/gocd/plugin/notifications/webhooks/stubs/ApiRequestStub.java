package com.kzrbill.gocd.plugin.notifications.webhooks.stubs;

import com.kzrbill.gocd.plugin.notifications.webhooks.requests.ApiRequest;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.RequestBody;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.RequestBodyProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestStub implements ApiRequest {

    private ArrayList<String> posts = new ArrayList<String>();
    private Map<String, String> headers = new HashMap<String, String>();
    private String body = "";

    public ApiRequest post(String apiUrl) {
        posts.add(apiUrl);
        return this;
    }

    public ApiRequest header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public RequestBody body(String bodyJson) {
        body = bodyJson;
        return new RequestBodyStub(bodyJson);
    }

    public String requestJsonSent() {
        return body;
    }

    public int totalPostsCalls() {
        return posts.size();
    }
}
