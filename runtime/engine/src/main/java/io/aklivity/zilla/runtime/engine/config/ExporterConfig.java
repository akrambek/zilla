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
package io.aklivity.zilla.runtime.engine.config;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;

public class ExporterConfig
{
    public final String namespace;
    public final String name;
    public final String qname;
    public final String type;
    public final String vault;
    public final OptionsConfig options;

    public transient long id;
    public transient long vaultId;

    public static ExporterConfigBuilder<ExporterConfig> builder()
    {
        return new ExporterConfigBuilder<>(identity());
    }

    ExporterConfig(
        String namespace,
        String name,
        String type,
        String vault,
        OptionsConfig options)
    {
        this.namespace = requireNonNull(namespace);
        this.name = requireNonNull(name);
        this.qname = String.format("%s:%s", namespace, name);
        this.type = requireNonNull(type);
        this.vault = vault;
        this.options = options;
    }
}
