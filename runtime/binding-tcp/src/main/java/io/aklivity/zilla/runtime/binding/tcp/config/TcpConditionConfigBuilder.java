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
package io.aklivity.zilla.runtime.binding.tcp.config;

import java.util.function.Function;

import org.agrona.collections.IntArrayList;

import io.aklivity.zilla.runtime.engine.config.ConditionConfig;
import io.aklivity.zilla.runtime.engine.config.ConfigBuilder;

public final class TcpConditionConfigBuilder<T> extends ConfigBuilder<T, TcpConditionConfigBuilder<T>>
{
    private final Function<ConditionConfig, T> mapper;

    private String cidr;
    private String authority;
    private IntArrayList ports;

    TcpConditionConfigBuilder(
        Function<ConditionConfig, T> mapper)
    {
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<TcpConditionConfigBuilder<T>> thisType()
    {
        return (Class<TcpConditionConfigBuilder<T>>) getClass();
    }

    public TcpConditionConfigBuilder<T> cidr(
        String cidr)
    {
        this.cidr = cidr;
        return this;
    }

    public TcpConditionConfigBuilder<T> authority(
        String authority)
    {
        this.authority = authority;
        return this;
    }

    public TcpConditionConfigBuilder<T> port(
        int port)
    {
        if (ports == null)
        {
            ports = new IntArrayList(1, -1);
        }
        ports.addInt(port);
        return this;
    }

    public TcpConditionConfigBuilder<T> ports(
        int[] ports)
    {
        this.ports = new IntArrayList(ports, ports.length, -1);
        return this;
    }

    @Override
    public T build()
    {
        final int[] portsArray = ports != null ? ports.toIntArray() : null;
        return mapper.apply(new TcpConditionConfig(cidr, authority, portsArray));
    }
}
