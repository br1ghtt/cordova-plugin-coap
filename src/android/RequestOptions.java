package com.phodal.plugin;

import org.json.JSONException;
import org.json.JSONObject;


public class RequestOptions {
    private final long TIMEOUT = 1000;

    private String protocol;
    private String host;
    private int port;

    private String path;
    private String payload;
    private String query;
    private String accept;
    private boolean useCons;


    private long timeout;


    public RequestOptions(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.payload = "";
        this.query = "";
        this.useCons = false;
        this.timeout = TIMEOUT;
    }

    public static RequestOptions jsonObjectToRequestOptions(JSONObject obj) throws JSONException {
        RequestOptions options = new RequestOptions(
                obj.getString("protocol"),
                obj.getString("host"),
                obj.getInt("port")
        );
        options.setPath(obj.optString("path", ""));
        options.setPayload(obj.optString("payload", ""));
        options.setQuery(obj.optString("query", ""));
        options.setAccept(obj.optString("accept", ""));
        options.setUseCons(obj.optBoolean("useCons", false));
        return options;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public boolean isUseCons() {
        return useCons;
    }

    public void setUseCons(boolean useCons) {
        this.useCons = useCons;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
