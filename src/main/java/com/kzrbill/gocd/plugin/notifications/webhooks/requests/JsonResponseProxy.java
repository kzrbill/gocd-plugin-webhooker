package com.kzrbill.gocd.plugin.notifications.webhooks.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class JsonResponseProxy implements JsonResponse {
    private HttpResponse<JsonNode> response;

    public JsonResponseProxy(HttpResponse<JsonNode> response) {
        this.response = response;
    }
}
