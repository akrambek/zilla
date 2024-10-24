/*
 * Copyright 2021-2024 Aklivity Inc
 *
 * Licensed under the Aklivity Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 *   https://www.aklivity.io/aklivity-community-license/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.aklivity.zilla.runtime.catalog.inline.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.aklivity.zilla.runtime.engine.config.ConfigBuilder;
import io.aklivity.zilla.runtime.engine.config.OptionsConfig;

public class InlineOptionsConfigBuilder<T> extends ConfigBuilder<T, InlineOptionsConfigBuilder<T>>
{
    private final Function<OptionsConfig, T> mapper;

    private List<InlineSchemaConfig> schemas;

    InlineOptionsConfigBuilder(
        Function<OptionsConfig, T> mapper)
    {
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<InlineOptionsConfigBuilder<T>> thisType()
    {
        return (Class<InlineOptionsConfigBuilder<T>>) getClass();
    }

    public InlineSchemaConfigBuilder<InlineOptionsConfigBuilder<T>> schema()
    {
        return new InlineSchemaConfigBuilder<>(this::schema);
    }

    public InlineOptionsConfigBuilder<T> schema(
        InlineSchemaConfig schema)
    {
        if (schemas == null)
        {
            schemas = new ArrayList<>();
        }
        schemas.add(schema);
        return this;
    }

    @Override
    public T build()
    {
        return mapper.apply(new InlineOptionsConfig(schemas));
    }
}
