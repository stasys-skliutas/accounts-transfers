package com.vzurauskas.accountstransfers

import static com.vzurauskas.accountstransfers.Environment.appUrl
import static com.vzurauskas.accountstransfers.RestClient.post

class TransferFixture {
    RestClient.RestResponse create(Map json, Map headers) {
        return post(appUrl() + 'transfers', json, headers)
    }
}
