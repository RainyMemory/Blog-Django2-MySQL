package com.sail.exp.freevoteapp.data.httpapi;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.data.util.Const;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ResponseHandlerTest {

    String testJsonParam1 = "{\n" +
            "\t\"django\":5.0\n" +
            "}";

    String testJsonParam2 = "{\n" +
            "\t\"id\":5.0\n" +
            "}\n";

    String checkKey = "5";

    Gson gson = new Gson();

    @Test
    public void id2Django() {
        String response = ResponseHandler.id2Django(testJsonParam2);
        Map<String, String> responseMap = gson.fromJson(response, Map.class);
        assertEquals(responseMap.get(Const.DJANGO_KEY), checkKey);
    }

    @Test
    public void django2Id() {
        String response = ResponseHandler.django2Id(testJsonParam1);
        Map<String, String> responseMap = gson.fromJson(response, Map.class);
        assertEquals(responseMap.get(Const.ID_KEY), checkKey);
    }
}