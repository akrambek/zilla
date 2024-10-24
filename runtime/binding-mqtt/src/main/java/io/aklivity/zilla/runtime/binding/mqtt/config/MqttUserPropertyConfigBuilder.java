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

import java.util.function.Function;

import io.aklivity.zilla.runtime.engine.config.ConfigBuilder;
import io.aklivity.zilla.runtime.engine.config.ModelConfig;

public class MqttUserPropertyConfigBuilder<T> extends ConfigBuilder<T, MqttUserPropertyConfigBuilder<T>>
{
    private final Function<MqttUserPropertyConfig, T> mapper;

    private String name;
    private ModelConfig value;

    MqttUserPropertyConfigBuilder(
        Function<MqttUserPropertyConfig, T> mapper)
    {
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<MqttUserPropertyConfigBuilder<T>> thisType()
    {
        return (Class<MqttUserPropertyConfigBuilder<T>>) getClass();
    }

    public MqttUserPropertyConfigBuilder<T> name(
        String name)
    {
        this.name = name;
        return this;
    }

    public MqttUserPropertyConfigBuilder<T> value(
        ModelConfig content)
    {
        this.value = content;
        return this;
    }

    public <C extends ConfigBuilder<MqttUserPropertyConfigBuilder<T>, C>> C value(
        Function<Function<ModelConfig, MqttUserPropertyConfigBuilder<T>>, C> value)
    {
        return value.apply(this::value);
    }

    @Override
    public T build()
    {
        return mapper.apply(new MqttUserPropertyConfig(name, value));
    }
}
