package com.kw_support.thirdlib.http.request;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kw_support.thirdlib.http.NetworkResponse;
import com.kw_support.thirdlib.http.ParseError;
import com.kw_support.thirdlib.http.Request;
import com.kw_support.thirdlib.http.Response;
import com.kw_support.thirdlib.http.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class GsonGetRequest<T> extends Request<T> {
    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;

    public GsonGetRequest(String url, Type type, Gson gson, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);

        this.gson = gson;
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return (Response<T>) Response.success
                    (
                            gson.fromJson(json, type),
                            HttpHeaderParser.parseCacheHeaders(response)
                    );
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
