package com.vzurauskas.accountstransfers;

import com.jolbox.bonecp.BoneCPDataSource;
import com.vzurauskas.accountstransfers.account.Accounts;
import com.vzurauskas.accountstransfers.account.JooqAccounts;
import com.vzurauskas.accountstransfers.api.Api;
import com.vzurauskas.accountstransfers.storage.Database;
import org.jooq.DSLContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public final class App {
    private static final AppConfig CONFIG = AppConfig.load("app.properties");

    public static void main(final String... args) throws SQLException, IOException {
        DSLContext db = new Database(dataSource()).connect();
        Accounts accounts = new JooqAccounts(db);
        Api.start(accounts);
    }

    private static DataSource dataSource() {
        final BoneCPDataSource src = new BoneCPDataSource();
        src.setDriverClass(CONFIG.datasourceDriverClass());
        src.setJdbcUrl(CONFIG.datasourceJdbcUrl());
        src.setUser(CONFIG.datasourceUser());
        src.setPassword(CONFIG.datasourcePassword());
        return src;
    }
}
