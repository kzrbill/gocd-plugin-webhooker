package com.kzrbill.gocd.plugin.notifications.webhooks.requests;

public interface ApiRequest {
    ApiRequest post(String apiUrl);
    ApiRequest header(String name, String value);
    RequestBody body(String stageStatusJson);
}

