/*
 * Copyright 2021-2024 Aklivity Inc.
 *
 * Aklivity licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.aklivity.zilla.runtime.binding.mqtt.config;

public final class MqttPatternConfig
{
    public final MqttConnectProperty property;
    public final String pattern;

    public MqttPatternConfig(
        MqttConnectProperty property,
        String pattern)
    {
        this.property = property;
        this.pattern = pattern;
    }

    public enum MqttConnectProperty
    {
        USERNAME,
        PASSWORD;

        public static MqttConnectProperty ofName(
            String value)
        {
            MqttConnectProperty field = null;
            switch (value)
            {
            case "username":
                field = USERNAME;
                break;
            case "password":
                field = PASSWORD;
                break;
            }
            return field;
        }
    }
}
