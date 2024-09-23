/*
 * Copyright 2021-2023 Aklivity Inc
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
package io.aklivity.zilla.runtime.binding.pgsql.kafka.internal.config;

import java.util.function.LongPredicate;

import io.aklivity.zilla.runtime.engine.config.RouteConfig;

public final class PgsqlKafkaRouteConfig
{
    public final long id;
    public final int order;

    private final LongPredicate authorized;

    public PgsqlKafkaRouteConfig(
        RouteConfig route)
    {
        this.id = route.id;
        this.order = route.order;
        this.authorized = route.authorized;
    }

    boolean authorized(
        long authorization)
    {
        return authorized.test(authorization);
    }
}