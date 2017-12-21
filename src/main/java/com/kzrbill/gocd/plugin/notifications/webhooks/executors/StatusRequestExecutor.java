/*
 * Copyright 2016 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kzrbill.gocd.plugin.notifications.webhooks.executors;

import com.kzrbill.gocd.plugin.notifications.webhooks.PluginRequest;
import com.kzrbill.gocd.plugin.notifications.webhooks.PluginSettings;
import com.kzrbill.gocd.plugin.notifications.webhooks.RequestExecutor;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.ApiRequest;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.ApiRequestProxy;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.JsonResponse;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.StatusRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.codec.binary.Hex;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.apache.commons.codec.digest.Sha2Crypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class StatusRequestExecutor implements RequestExecutor {

    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    public static final Logger LOG = Logger.getLoggerFor(StatusRequestExecutor.class);

    private final StatusRequest request;
    private final PluginRequest pluginRequest;
    private final ApiRequest apiRequest;

    public StatusRequestExecutor(StatusRequest request, PluginRequest pluginRequest, ApiRequest apiRequest) {
        this.request = request;
        this.pluginRequest = pluginRequest;
        this.apiRequest = apiRequest;
    }

    public StatusRequestExecutor(StatusRequest request, PluginRequest pluginRequest) {
        this(request, pluginRequest, new ApiRequestProxy());
    }

    @Override
    public GoPluginApiResponse execute() throws Exception {
        HashMap<String, Object> responseJson = new HashMap<>();
        try {
            sendNotification();
            responseJson.put("status", "success");
        } catch (Exception e) {
            responseJson.put("status", "failure");
            responseJson.put("message", e.getMessage());
        }
        return new DefaultGoPluginApiResponse(200, GSON.toJson(responseJson));
    }

    protected void sendNotification() throws Exception {
        this.postStageToApi();
    }

    private void postStageToApi() throws Exception {
        String stageStatusJson = request.toJson();
        PluginSettings settings = this.pluginRequest.getPluginSettings();
        JsonResponse jsonResponse = this.apiRequest.post(settings.getApiUrl())
                .header("X-Hub-Signature", makeToken(settings.getSecret(), stageStatusJson))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(stageStatusJson)
                .asJson();
    }

    public static String makeToken(String secret, String data) throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        byte[] secretBytes = secret.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
        hmacSHA256.init(secretKey);
        byte[] dataBytes = data.getBytes("UTF-8");
        return Hex.encodeHexString(hmacSHA256.doFinal(dataBytes));
    }
}
