package com.vzurauskas.accountstransfers

import static com.vzurauskas.accountstransfers.Environment.appUrl
import static com.vzurauskas.accountstransfers.RestClient.get
import static com.vzurauskas.accountstransfers.RestClient.post

class AccountFixture {
    RestClient.RestResponse create(Map json) {
        return post(appUrl() + 'accounts', json)
    }

    RestClient.RestResponse fetch(String id) {
        return get(appUrl() + 'accounts/' + id)
    }
}
