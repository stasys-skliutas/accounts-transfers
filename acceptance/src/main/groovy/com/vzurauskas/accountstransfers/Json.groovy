package com.vzurauskas.accountstransfers

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class Json {
    static Object readJson(InputStream inputStream) {
        return new JsonSlurper().parse(inputStream);
    }

    static String toJson(Object obj) {
        return new JsonBuilder(obj).toString();
    }
}
