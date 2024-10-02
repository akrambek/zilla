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
package io.aklivity.zilla.runtime.binding.risingwave.internal.statement;

import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;

public class RisingwaveCreateMaterializedViewTemplate extends RisingwaveCommandTemplate
{
    private final String sqlFormat = """
        CREATE MATERIALIZED VIEW IF NOT EXISTS %s AS %s;\u0000""";

    public RisingwaveCreateMaterializedViewTemplate()
    {
    }

    public String generate(
        CreateView createView)
    {
        String view = createView.getView().getName();
        String select = createView.getSelect().toString();

        return String.format(sqlFormat, view, select);
    }

    public String generate(
        RisingwaveCreateTableCommand command)
    {
        CreateTable createTable = command.createTable;
        String name = createTable.getTable().getName();

        return String.format(sqlFormat, "%s_view".formatted(name), "SELECT * FROM %s_source".formatted(name));
    }
}
