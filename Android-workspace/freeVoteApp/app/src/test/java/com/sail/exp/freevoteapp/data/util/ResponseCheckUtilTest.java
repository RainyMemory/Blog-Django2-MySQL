package com.sail.exp.freevoteapp.data.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseCheckUtilTest {

    String errorMsg = "{\n" +
            "    \"ResponseCode\":\"696\", \n" +
            "    \"ResponseMessag\":\"ERROR_MESSAGE\"\n" +
            "}";

    @Test
    public void checkResMessage() {
        boolean response = ResponseCheckUtil.CheckResMessage(errorMsg);
        assertFalse(response);
    }
}