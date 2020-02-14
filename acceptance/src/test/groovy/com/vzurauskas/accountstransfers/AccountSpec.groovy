package com.vzurauskas.accountstransfers

import spock.lang.Shared
import spock.lang.Specification

class AccountSpec extends Specification {
    @Shared
    def fixture = new AccountFixture()

    def 'create account'() {
        given:
            def req = [
                'iban': 'DE89370400440532666000',
                'currency': 'EUR'
            ]
        when:
            def res = fixture.create(req)
        then:
            res.status == 201
            with(res.body) {
                id
                iban == 'DE89370400440532666000'
                currency == 'EUR'
            }
            with(res.body.balance) {
                amount == '0.00'
                currency == 'EUR'
            }
    }

    def 'get account'() {
        given:
            def creationResponse = fixture.create([
                'iban': 'DE89370400440532666000',
                'currency': 'EUR'
            ])
            def accountId = creationResponse.body.id
        when:
            def res = fixture.fetch(accountId)
        then:
            res.status == 200
            with(res.body) {
                id
                iban == 'DE89370400440532666000'
                currency == 'EUR'
            }
            with(res.body.balance) {
                amount == '0.00'
                currency == 'EUR'
            }
    }
}
