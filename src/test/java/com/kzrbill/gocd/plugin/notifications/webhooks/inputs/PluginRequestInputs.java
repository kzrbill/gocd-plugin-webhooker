package com.kzrbill.gocd.plugin.notifications.webhooks.inputs;

import com.kzrbill.gocd.plugin.notifications.webhooks.PluginRequest;
import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.request.GoApiRequest;
import com.thoughtworks.go.plugin.api.response.GoApiResponse;

import java.util.HashMap;
import java.util.Map;

public class PluginRequestInputs {

    public static PluginRequest withSettingsJson(final String settingsJson) {

        return new PluginRequest(new GoApplicationAccessor() {
            @Override
            public GoApiResponse submit(GoApiRequest request) {
                return new GoApiResponse() {
                    @Override
                    public int responseCode() {
                        return 200;
                    }

                    @Override
                    public Map<String, String> responseHeaders() {
                        return new HashMap<>();
                    }

                    @Override
                    public String responseBody() {
                        return settingsJson;
                    }
                };
            }
        });
    }

}
