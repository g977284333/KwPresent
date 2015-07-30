package com.gechen.keepwalking.kw.manager;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kw_support.manager.http.GsonGetRequest;
import com.kw_support.manager.http.GsonPostRequest;

import java.util.ArrayList;

public class ApiRequest
{
    public static GsonGetRequest<DummyObject> getDummyObject
    (
            Response.Listener<DummyObject> listener,
            Response.ErrorListener errorListener
    )
    {
        final String url = "http://www.mocky.io/v2/55973508b0e9e4a71a02f05f";

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
                .create();

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<DummyObject>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    public static GsonGetRequest<ArrayList<DummyObject>> getDummyObjectArray
    (
            Response.Listener<ArrayList<DummyObject>> listener,
            Response.ErrorListener errorListener
    )
    {
        final String url = "http://www.mocky.io/v2/5597d86a6344715505576725";

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
                .create();

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<ArrayList<DummyObject>>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    public static GsonPostRequest getDummyObjectArrayWithPost
            (
                    Response.Listener<DummyObject> listener,
                    Response.ErrorListener errorListener
            )
    {
        final String url = "http://PostApiEndpoint";
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
                .create();

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Ficus");
        jsonObject.addProperty("surname", "Kirkpatrick");

        final JsonArray squareGuys = new JsonArray();
        final JsonObject dev1 = new JsonObject();
        final JsonObject dev2 = new JsonObject();
        dev1.addProperty("name", "Jake Wharton");
        dev2.addProperty("name", "Jesse Wilson");
        squareGuys.add(dev1);
        squareGuys.add(dev2);

        jsonObject.add("squareGuys", squareGuys);

        final GsonPostRequest gsonPostRequest = new GsonPostRequest<>
                (
                        url,
                        jsonObject.toString(),
                        new TypeToken<DummyObject>()
                        {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );

        gsonPostRequest.setShouldCache(false);

        return gsonPostRequest;
    }
}
