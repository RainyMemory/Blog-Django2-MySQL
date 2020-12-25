package com.sail.exp.freevoteapp.data.httpapi;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.data.util.Const;

import java.util.Map;

public class ResponseHandler {

    public static String id2Django(String response) {
        Gson gson = new Gson();
        Map responseMap = gson.fromJson(response, Map.class);
        String id2django = "" +  responseMap.get(Const.ID_KEY);
        if (id2django.indexOf('.') != -1) {
            id2django = id2django.substring(0, id2django.indexOf('.'));
        }
        responseMap.remove(Const.ID_KEY);
        responseMap.put(Const.DJANGO_KEY, id2django);
        return gson.toJson(responseMap);
    }

    public static String django2Id(String request) {
        Gson gson = new Gson();
        Map requestMap = gson.fromJson(request, Map.class);
        String django2id = "" + requestMap.get(Const.DJANGO_KEY);
        if (django2id.indexOf('.') != -1) {
            django2id = django2id.substring(0, django2id.indexOf('.'));
        }
        requestMap.remove(Const.DJANGO_KEY);
        requestMap.put(Const.ID_KEY, django2id);
        return gson.toJson(requestMap);
    }

}
