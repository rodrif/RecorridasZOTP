package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 23/10/2015.
 */
public abstract class   BasicJsonUtil<T> {

    public abstract Class getClase();

    public abstract T fromJsonObject(JSONObject tJson) throws Exception;

    public abstract JSONObject toJsonObject(T t) throws Exception;

    public List<T> fromJsonString(String jsonString) throws Exception {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<T> ts = new ArrayList<T>();

        for (int i = 0; i < jsonArray.length(); ++i) {
            T t = fromJsonObject(jsonArray.getJSONObject(i));
            ts.add(t);
        }

        return ts;
    }

    public String toJsonString(List<T> ts) throws Exception {
        ArrayList<String> stringArray = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray();

        for (T t : ts) {
            jsonArray.put(toJsonObject(t));
        }

        for (int i = 0, count = jsonArray.length(); i < count; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                stringArray.add(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArray.toString();
    }

    public String toJSonValue(T t) throws Exception {
        JSONObject jsonObj = toJsonObject(t);

        if (jsonObj != null)
            return jsonObj.toString();
        return null;
    }

}
