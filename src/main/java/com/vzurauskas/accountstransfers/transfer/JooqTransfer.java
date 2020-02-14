package com.vzurauskas.accountstransfers.transfer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.vzurauskas.accountstransfers.account.Account;
import com.vzurauskas.accountstransfers.common.Amount;
import com.vzurauskas.accountstransfers.common.UncheckedMapper;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public final class JooqTransfer implements Transfer {

    private static final UncheckedMapper mapper = new UncheckedMapper();

    private final DSLContext db;
    private final UUID id;
    private final Account debtor;
    private final Account creditor;
    private final BigDecimal amount;
    private final String currency;
    private final Map<String, String> headers;

    public JooqTransfer(
        DSLContext db,
        Account debtor,
        Account creditor,
        BigDecimal amount,
        String currency,
        Map<String, String> headers
    ) {
        this.db = db;
        this.id = UUID.randomUUID();
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
        this.currency = currency;
        this.headers = headers;
    }

    @Override
    public void execute() {
        db
            .insertInto(
                DSL.table("TRANSFER"),
                DSL.field("ID"),
                DSL.field("TIMESTAMP"),
                DSL.field("DEBTOR"),
                DSL.field("CREDITOR"),
                DSL.field("AMOUNT"),
                DSL.field("CURRENCY"),
                DSL.field("CLIENT_ID"),
                DSL.field("IDEMPOTENCY_KEY")
            )
            .values(
                id,
                OffsetDateTime.now(),
                debtor.id(),
                creditor.id(),
                amount,
                currency,
                headers.get("x-client-id"),
                headers.get("x-idempotency-key")
            )
            .execute();
    }

    @Override
    public JsonNode json() {
        ObjectNode transfer = mapper.objectNode();
        transfer.put("debtor", debtor.iban());
        transfer.put("creditor", creditor.iban());
        transfer.set("instructedAmount", new Amount(amount, currency).json());
        return transfer;
    }
}
