package io.ribot.app.util;

import com.google.gson.Gson;

/**
 * Created by tiantong on 2017/4/17.
 */

public class GsonUtils {
    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static Gson getGson() {
        return gson;
    }
}
