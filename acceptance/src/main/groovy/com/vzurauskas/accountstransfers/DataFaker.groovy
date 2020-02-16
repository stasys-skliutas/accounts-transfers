package com.vzurauskas.accountstransfers

import com.github.javafaker.Faker

class DataFaker {
    static String randomString(int length) {
        return Faker.instance().lorem().characters(length)
    }
}
