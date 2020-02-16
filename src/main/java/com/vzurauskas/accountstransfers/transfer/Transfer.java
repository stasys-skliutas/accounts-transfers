package com.vzurauskas.accountstransfers.transfer;

import com.fasterxml.jackson.databind.JsonNode;

public interface Transfer {
    void execute();
    JsonNode json();
}
