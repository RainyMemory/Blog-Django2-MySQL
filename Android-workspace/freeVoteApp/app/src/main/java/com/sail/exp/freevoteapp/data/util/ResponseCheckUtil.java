package com.sail.exp.freevoteapp.data.util;

import com.google.gson.Gson;

import java.util.Map;

public class ResponseCheckUtil {

    public static boolean CheckResMessage(String jsonStr) {
        Gson gson = new Gson();
        Map paraMap = gson.fromJson(jsonStr, Map.class);
        if (null == paraMap || paraMap.isEmpty()) {
            return false;
        } else if (null != paraMap.get(Const.RSP_FLAG) && paraMap.get(Const.RSP_FLAG).equals(Const.SERVER_EXP)) {
            return false;
        } else {
            return true;
        }
    }

}
