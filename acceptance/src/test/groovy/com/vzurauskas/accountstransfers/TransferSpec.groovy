package com.vzurauskas.accountstransfers

import spock.lang.Shared
import spock.lang.Specification

import static com.vzurauskas.accountstransfers.DataFaker.randomString

class TransferSpec extends Specification {
    @Shared
    def accountFixture = new AccountFixture()
    @Shared
    def transferFixture = new TransferFixture()

    def 'execute transfer between accounts'() {
        given:
            def creditorIban = randomString(34)
            def creditorId = createAccount(creditorIban)
            def debtorIban = randomString(34)
            def debtorId = createAccount(debtorIban)
            def body = [
                creditor: creditorIban,
                debtor: debtorIban,
                instructedAmount: [
                    amount: '10.00',
                    currency: 'EUR'
                ]
            ]
            def headers = [
                'x-client-id': 'client',
                'x-idempotency-key': randomString(40)
            ]
        when:
            def req1 = transferFixture.create(body, headers)
            def debtorAccount = accountFixture.fetch(debtorId).body
            def creditorAccount = accountFixture.fetch(creditorId).body
        then:
            req1.status == 200
            debtorAccount?.balance?.amount == '-10.00'
            creditorAccount?.balance?.amount == '10.00'
    }

    def 'should not execute duplicate transfer'() {
        given:
            def creditorIban = randomString(34)
            def creditorId = createAccount(creditorIban)
            def debtorIban = randomString(34)
            def debtorId = createAccount(debtorIban)
            def body = [
                creditor: creditorIban,
                debtor: debtorIban,
                instructedAmount: [
                    amount: '10.00',
                    currency: 'EUR'
                ]
            ]
            def headers = [
                'x-client-id': 'client',
                'x-idempotency-key': randomString(40)
            ]
        when:
            def req1 = transferFixture.create(body, headers)
            def req2 = transferFixture.create(body, headers)
            def debtorAccount = accountFixture.fetch(debtorId).body
            def creditorAccount = accountFixture.fetch(creditorId).body
        then:
            req1.status == 200
            req2.status == 200
            debtorAccount?.balance?.amount == '-10.00'
            creditorAccount?.balance?.amount == '10.00'
    }

    private String createAccount(String iban) {
        return accountFixture.create([
            iban: iban,
            currency: 'EUR'
        ]).body.id
    }
}
