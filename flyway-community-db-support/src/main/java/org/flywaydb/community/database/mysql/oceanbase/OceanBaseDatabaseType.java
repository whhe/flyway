/*
 * Copyright (C) Red Gate Software Ltd 2010-2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.community.database.mysql.oceanbase;

import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.internal.database.base.Database;
import org.flywaydb.core.internal.jdbc.JdbcConnectionFactory;
import org.flywaydb.core.internal.jdbc.StatementInterceptor;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.database.mysql.MySQLDatabaseType;

public class OceanBaseDatabaseType extends MySQLDatabaseType {

    public static final String JDBC_URL_PREFIX = "jdbc:oceanbase:";
    public static final String DRIVER_CLASS = "com.oceanbase.jdbc.Driver";
    public static final String LEGACY_DRIVER_CLASS = "com.alipay.oceanbase.jdbc.Driver";

    @Override
    public String getName() {
        return "OceanBase";
    }

    @Override
    public int getPriority() {
        // OceanBase needs to be checked in advance of MySql
        return 1;
    }

    @Override
    public boolean handlesJDBCUrl(String url) {
        return url.startsWith(JDBC_URL_PREFIX) || super.handlesJDBCUrl(url);
    }

    @Override
    public String getDriverClass(String url, ClassLoader classLoader) {
        return url.startsWith(JDBC_URL_PREFIX) ? DRIVER_CLASS : super.getDriverClass(url, classLoader);
    }

    @Override
    public String getBackupDriverClass(String url, ClassLoader classLoader) {
        if (ClassUtils.isPresent(LEGACY_DRIVER_CLASS, classLoader)) {
            return LEGACY_DRIVER_CLASS;
        }
        return super.getBackupDriverClass(url, classLoader);
    }

    @Override
    public Database createDatabase(Configuration configuration, JdbcConnectionFactory jdbcConnectionFactory, StatementInterceptor statementInterceptor) {
        return new OceanBaseDatabase(configuration, jdbcConnectionFactory, statementInterceptor);
    }
}