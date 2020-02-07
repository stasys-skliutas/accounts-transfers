package com.vzurauskas.accountstransfers.storage.fakejooq;

import java.sql.SQLException;

import org.jooq.tools.jdbc.MockResult;

public interface Statement {

    boolean matches(String sql);
    MockResult[] execute(Object[] bindings) throws SQLException;
}
