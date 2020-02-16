package com.vzurauskas.accountstransfers

import static java.util.Optional.ofNullable

class Environment {
    static Properties TEST_PROPERTIES = new Properties()
    static {
        TEST_PROPERTIES.load(Environment.class.getClassLoader().getResourceAsStream('test.properties'))
    }

    static String appUrl() {
        return ofNullable(System.getenv().get('ACCOUNTSTRANSFERS_APP_URL')).orElseGet {
            return ofNullable(TEST_PROPERTIES.getProperty('default.app.url')).orElseThrow {
                return new IllegalStateException('Unable to resolve app url to use in tests. Check test.properties ' +
                    'file or set with APP_URL environment variable')
            }
        }
    }
}
