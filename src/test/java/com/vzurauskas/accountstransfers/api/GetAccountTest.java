package com.vzurauskas.accountstransfers.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.UUID;

import com.vzurauskas.accountstransfers.account.GetAccount;
import org.junit.jupiter.api.Test;

import com.vzurauskas.accountstransfers.account.Accounts;
import com.vzurauskas.accountstransfers.storage.FakeDatabase;
import com.vzurauskas.accountstransfers.account.JooqAccounts;
import com.vzurauskas.accountstransfers.common.UncheckedMapper;
import org.takes.rq.RqFake;

final class GetAccountTest {

    private final UncheckedMapper mapper = new UncheckedMapper();

    private final Accounts accounts = new JooqAccounts(
        new FakeDatabase().connect()
    );

    @Test
    void getsAccountWithNoTransactions() throws IOException {
        UUID account = accounts.add("DE89370400440532666000", "EUR");
        assertEquals(
            mapper.objectNode()
                .put("id", account.toString())
                .put("iban", "DE89370400440532666000")
                .put("currency", "EUR")
                .set(
                    "balance",
                    mapper.objectNode()
                        .put("amount", "0.00")
                        .put("currency", "EUR")
                ),
            mapper.json(
                new GetAccount(accounts).act(
                    new RqFake(
                        "GET", "/accounts/" + account
                    )
                ).body()
            )
        );
    }
}