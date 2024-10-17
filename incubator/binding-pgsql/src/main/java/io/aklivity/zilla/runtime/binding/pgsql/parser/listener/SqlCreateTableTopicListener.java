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
package io.aklivity.zilla.runtime.binding.pgsql.parser.listener;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.agrona.collections.ObjectHashSet;

import io.aklivity.zilla.runtime.binding.pgsql.parser.PostgreSqlParser;
import io.aklivity.zilla.runtime.binding.pgsql.parser.PostgreSqlParserBaseListener;
import io.aklivity.zilla.runtime.binding.pgsql.parser.module.TableInfo;

public class SqlCreateTableTopicListener extends PostgreSqlParserBaseListener
{
    private String name;
    private final Map<String, String> columns = new LinkedHashMap<>();
    private final Set<String> primaryKeys = new ObjectHashSet<>();

    public TableInfo tableInfo()
    {
        return new TableInfo(name, columns, primaryKeys);
    }

    @Override
    public void enterQualified_name(
        PostgreSqlParser.Qualified_nameContext ctx)
    {
        name = ctx.getText();
    }

    @Override
    public void enterCreatestmt(
        PostgreSqlParser.CreatestmtContext ctx)
    {
        columns.clear();
        primaryKeys.clear();

        if (ctx.opttableelementlist().tableelementlist() != null)
        {
            for (PostgreSqlParser.TableelementContext tableElement : ctx.opttableelementlist().tableelementlist().tableelement())
            {
                if (tableElement.columnDef() != null)
                {
                    String columnName = tableElement.columnDef().colid().getText();
                    String dataType = tableElement.columnDef().typename().getText();
                    columns.put(columnName, dataType);

                    for (PostgreSqlParser.ColconstraintContext constraint :
                        tableElement.columnDef().colquallist().colconstraint())
                    {
                        if (constraint.colconstraintelem().PRIMARY() != null &&
                            constraint.colconstraintelem().KEY() != null)
                        {
                            primaryKeys.add(columnName);
                        }
                    }
                }
                else if (tableElement.tableconstraint() != null)
                {
                    if (tableElement.tableconstraint().constraintelem().PRIMARY() != null &&
                        tableElement.tableconstraint().constraintelem().KEY() != null)
                    {
                        tableElement.tableconstraint().constraintelem().columnlist().columnElem().forEach(
                            column -> primaryKeys.add(column.getText()));
                    }
                }
            }
        }
    }
}
