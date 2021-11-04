package com.fjt.util;

import com.google.gson.Gson;

/**
 * 更好的返回提供json数据
 */
public class JSONUtil {
    private static Gson gson = new Gson();

    public static String toJSON(Object obj) {
        return gson.toJson(obj);
    }
}
