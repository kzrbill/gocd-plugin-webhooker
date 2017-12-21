package com.kzrbill.gocd.plugin.notifications.webhooks.stubs;

import com.kzrbill.gocd.plugin.notifications.webhooks.requests.ApiRequest;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.RequestBody;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.RequestBodyProxy;

public class ApiRequestStub implements ApiRequest {
    public ApiRequest post(String apiUrl) {
        return this;
    }

    public ApiRequest header(String name, String value) {
        return this;
    }

    public RequestBody body(String stageStatusJson) {
        return new RequestBodyStub(stageStatusJson);
    }
}
