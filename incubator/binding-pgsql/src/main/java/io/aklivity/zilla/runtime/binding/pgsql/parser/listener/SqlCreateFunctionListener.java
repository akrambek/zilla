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

import java.util.ArrayList;
import java.util.List;

import io.aklivity.zilla.runtime.binding.pgsql.parser.PostgreSqlParser;
import io.aklivity.zilla.runtime.binding.pgsql.parser.PostgreSqlParserBaseListener;
import io.aklivity.zilla.runtime.binding.pgsql.parser.module.FunctionArgument;
import io.aklivity.zilla.runtime.binding.pgsql.parser.module.FunctionInfo;

public class SqlCreateFunctionListener extends PostgreSqlParserBaseListener
{
    private String name;
    private final List<FunctionArgument> arguments = new ArrayList<>();
    private String returnType;
    private String asFunction;
    private String language;

    public FunctionInfo functionInfo()
    {
        return new FunctionInfo(name, arguments, returnType, asFunction, language);
    }

    @Override
    public void enterCreatefunctionstmt(
        PostgreSqlParser.CreatefunctionstmtContext ctx)
    {
        name = ctx.func_name().getText();
    }

    @Override
    public void enterFunc_args(
        PostgreSqlParser.Func_argsContext ctx)
    {
        ctx.func_args_list().func_arg().forEach(arg ->
        {
            String argName = arg.param_name() != null ? arg.param_name().getText() : null;
            String argType = arg.func_type().getText();
            arguments.add(new FunctionArgument(argName, argType));
        });
    }

    @Override
    public void enterFunc_type(
        PostgreSqlParser.Func_typeContext ctx)
    {
        returnType = ctx.typename().getText();
    }

    @Override
    public void enterFunc_as(
        PostgreSqlParser.Func_asContext ctx)
    {
        asFunction = ctx.getText();
    }

    @Override
    public void enterNonreservedword_or_sconst(
        PostgreSqlParser.Nonreservedword_or_sconstContext ctx)
    {
        language = ctx.getText();
    }
}
