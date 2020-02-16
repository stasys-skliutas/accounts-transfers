package com.vzurauskas.accountstransfers.storage;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class Database {
    private final DataSource dataSource;

    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DSLContext connect() throws SQLException {
        DSLContext context = DSL.using(dataSource.getConnection(), SQLDialect.MYSQL);
        initTables(context);
        return context;
    }

    private static void initTables(DSLContext context) {
        context.createTableIfNotExists("ACCOUNT")
            .column("ID", SQLDataType.UUID.nullable(false))
            .column("IBAN", SQLDataType.VARCHAR.length(34).nullable(false))
            .column("CURRENCY", SQLDataType.VARCHAR.length(8).nullable(false))
            .constraints(
                DSL.constraint("PK_ACCOUNT").primaryKey("ID")
            )
            .execute();
        context.createTableIfNotExists("TRANSFER")
            .column("ID", SQLDataType.UUID.nullable(false))
            .column("TIMESTAMP", SQLDataType.TIMESTAMP.nullable(false))
            .column("DEBTOR", SQLDataType.UUID.nullable(false))
            .column("CREDITOR", SQLDataType.UUID.nullable(false))
            .column("AMOUNT", SQLDataType.DECIMAL.nullable(false))
            .column("CURRENCY", SQLDataType.VARCHAR(8).nullable(false))
            .column("CLIENT_ID", SQLDataType.VARCHAR(40).nullable(false))
            .column("IDEMPOTENCY_KEY", SQLDataType.VARCHAR(40).nullable(false))
            .constraints(
                DSL.constraint("PK_TRANSFER").primaryKey("ID"),
                DSL.constraint("IP_TRANSFER").unique("CLIENT_ID", "IDEMPOTENCY_KEY")
            )
            .execute();
    }
}
