package com.kzrbill.gocd.plugin.notifications.webhooks.stubs;

import com.kzrbill.gocd.plugin.notifications.webhooks.requests.JsonResponse;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.RequestBody;

public class RequestBodyStub implements RequestBody {
    private String stageStatusJson;

    public RequestBodyStub(String stageStatusJson) {
        this.stageStatusJson = stageStatusJson;
    }

    public JsonResponse asJson() throws Exception {
        return new JsonResponseStub();
    }
}
