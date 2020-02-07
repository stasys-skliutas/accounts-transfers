package com.vzurauskas.accountstransfers.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

import com.vzurauskas.accountstransfers.account.PostAccount;
import org.junit.jupiter.api.Test;

import com.vzurauskas.accountstransfers.account.Accounts;
import com.vzurauskas.accountstransfers.common.Amount;
import com.vzurauskas.accountstransfers.storage.FakeDatabase;
import com.vzurauskas.accountstransfers.account.JooqAccounts;
import com.vzurauskas.accountstransfers.common.UncheckedMapper;
import org.takes.rq.RqFake;

final class PostAccountTest {

    private final UncheckedMapper mapper = new UncheckedMapper();

    private final Accounts accounts = new JooqAccounts(
        new FakeDatabase().connect()
    );

    @Test
    void postsAccount() throws IOException {
        JsonNode response = mapper.json(
            new PostAccount(accounts).act(
                new RqFake(
                    "POST", "/accounts",
                    mapper.string(
                        mapper.objectNode()
                            .put("iban", "DE89370400440532013000")
                            .put("currency", "EUR")
                    )
                )
            ).body()
        );
        assertAll(
            () -> assertEquals("DE89370400440532013000", response.get("iban").textValue()),
            () -> assertEquals("EUR", response.get("currency").textValue()),
            () -> assertEquals(new Amount(BigDecimal.ZERO, "EUR").json(), response.get("balance"))
        );
    }
}