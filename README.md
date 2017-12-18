# GoCD Webhooks Plugin

Simple webhooks plugin so go can send stage status update to your rest API.

Configure a rest endpoint to receive POST request from go with payloads that look like this:

```json
{
  "pipeline": {
    "name": "gometric_pull_and_accept_pipeline",
    "counter": "41",
    "group": "go",
    "build-cause": [
      {
        "material": {
          "git-configuration": {
            "shallow-clone": false,
            "branch": "master",
            "url": "https://github.com/kzrbill/gometrics"
          },
          "type": "git"
        },
        "changed": true,
        "modifications": [
          {
            "revision": "dd281569af5177bc8f8022c940574d5483b7f72a",
            "modified-time": "Dec 9, 2017 11:46:18 AM",
            "data": {}
          }
        ]
      }
    ],
    "stage": {
      "name": "node_accepts_stage",
      "counter": "19",
      "approval-type": "success",
      "approved-by": "gouser",
      "state": "Building",
      "result": "Unknown",
      "create-time": "Dec 13, 2017 4:06:00 PM",
      "jobs": [
        {
          "name": "npm_test_job",
          "schedule-time": "Dec 13, 2017 4:06:00 PM",
          "state": "Scheduled",
          "result": "Unknown"
        }
      ]
    }
  }
}
```

Until the functionality is added to the plugin, you will need to update the URL in StatusRequestExecutor postStageToApi method, then build.

## Building the code base

To build the jar, run `./gradlew clean test assemble`

## Deploy

Copy the jar product to Go Server/plugins/external dir

Restart the Go Server.

## License

```plain
Copyright 2016 ThoughtWorks, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## About the license and releasing your plugin under a different license

The skeleton code in this repository is licensed under the Apache 2.0 license. The license itself specifies the terms
under which derivative works may be distributed (the license also defines derivative works). The Apache 2.0 license is a
permissive open source license that has minimal requirements for downstream licensors/licensees to comply with.

This does not prevent your plugin from being licensed under a different license as long as you comply with the relevant
clauses of the Apache 2.0 license (especially section 4). Typically, you clone this repository and keep the existing
copyright notices. You are free to add your own license and copyright notice to any modifications.

This is not legal advice. Please contact your lawyers if needed.
