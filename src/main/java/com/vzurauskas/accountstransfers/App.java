package com.vzurauskas.accountstransfers;

import com.vzurauskas.accountstransfers.account.Accounts;
import com.vzurauskas.accountstransfers.account.JooqAccounts;
import com.vzurauskas.accountstransfers.api.Api;
import com.vzurauskas.accountstransfers.storage.Database;
import org.jooq.DSLContext;

import java.io.IOException;
import java.sql.SQLException;

public final class App {

    public static void main(final String... args) throws SQLException, IOException {
        DSLContext db = new Database().connect();
        Accounts accounts = new JooqAccounts(db);
        Api.start(accounts);
    }
}
