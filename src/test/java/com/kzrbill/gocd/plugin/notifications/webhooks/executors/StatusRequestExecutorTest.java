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
import com.kzrbill.gocd.plugin.notifications.webhooks.inputs.PluginRequestInputs;
import com.kzrbill.gocd.plugin.notifications.webhooks.requests.StatusRequest;
import com.kzrbill.gocd.plugin.notifications.webhooks.stubs.ApiRequestStub;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StatusRequestExecutorTest {

    @Test
    public void postsStatusUpdatesToTheApi() throws Exception {
        ApiRequestStub apiRequest =  new ApiRequestStub();
        PluginRequest pluginRequest2 = PluginRequestInputs.withSettingsJson("{ \"api_url\": \"https://some.api.url/go-status-update/\" }");
        StatusRequest statusRequest = StatusRequest.fromJSON("{\"pipeline\":{\"name\":\"A pipeline name\"}}");
        new StatusRequestExecutor(statusRequest, pluginRequest2, apiRequest)
                .execute();

        Assert.assertEquals(1, apiRequest.totalPostsCalls());
        Assert.assertEquals("{\"pipeline\":{\"name\":\"A pipeline name\"}}", apiRequest.requestJsonSent());
    }

    @Test
    public void shouldRenderASuccessResponseIfNotificationWasSent() throws Exception {
        GoPluginApiResponse response = new StatusRequestExecutor(null, null) {
            @Override
            protected void sendNotification() {
                // do nothing!
            }
        }.execute();

        assertThat(response.responseCode(), is(200));
        JSONAssert.assertEquals("{\"status\":\"success\"}", response.responseBody(), true);
    }

    @Test
    public void shouldRenderAnErrorResponseIfNotificationWasNotSent() throws Exception {
        GoPluginApiResponse response = new StatusRequestExecutor(null, null) {
            @Override
            protected void sendNotification() {
                throw new RuntimeException("Boom!");
            }
        }.execute();

        assertThat(response.responseCode(), is(200));
        JSONAssert.assertEquals("{\"status\":\"failure\",\"message\":\"Boom!\"}", response.responseBody(), true);
    }
}
