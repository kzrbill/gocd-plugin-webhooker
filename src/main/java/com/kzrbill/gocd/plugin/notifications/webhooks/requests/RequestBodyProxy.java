package com.kzrbill.gocd.plugin.notifications.webhooks.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.body.RequestBodyEntity;

public class RequestBodyProxy implements RequestBody {

    private final RequestBodyEntity request;
    public RequestBodyProxy(RequestBodyEntity requestBody) {
        this.request = requestBody;
    }

    public JsonResponse asJson() throws Exception {
        return new JsonResponseProxy(this.request.asJson());
    }
}
