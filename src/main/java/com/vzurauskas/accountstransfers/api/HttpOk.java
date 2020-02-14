package com.vzurauskas.accountstransfers.api;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;

import com.vzurauskas.accountstransfers.common.UncheckedMapper;
import org.takes.Response;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;

public final class HttpOk implements Response {

    private final UncheckedMapper mapper = new UncheckedMapper();

    private final Response origin;

    public HttpOk(JsonNode response) {
        this.origin = new RsWithType(
            new RsWithStatus(
                new RsWithBody(
                    mapper.bytes(response)
                ),
                200
            ),
            "application/json"
        );
    }

    @Override
    public InputStream body() throws IOException {
        return origin.body();
    }

    @Override
    public Iterable<String> head() throws IOException {
        return origin.head();
    }
}
