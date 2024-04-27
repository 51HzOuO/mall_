package com.demo.mall1.utils;

import com.google.gson.Gson;

public class GetGson {
    private static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }
}
