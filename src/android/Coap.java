package com.phodal.plugin;

import com.google.gson.Gson;
import org.apache.cordova.*;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class Coap extends CordovaPlugin {

    private static String TAG = "CoapClient";

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, data.toString());
        RequestOptions options;
        try {
            JSONObject jsonObj = data.getJSONObject(0);
            options = jsonObjectToRequestOptions(jsonObj);
        } catch (JSONException ex) {
            return false;
        }
        if (action.equals("get")) {
            return get(options, callbackContext);
        } else if(action.equals("post")) {
            return post(options, callbackContext);
        } else if(action.equals("put")) {
            return put(options, callbackContext);
        } else if(action.equals("delete")) {
            return delete(options, callbackContext);
        } else if (action.equals("ping")) {
            return ping(options, callbackContext);
        } else if (action.equals("discover")) {
            return discover(options, callbackContext);
        } else {
            return false;
        }
    }

    private boolean ping(RequestOptions options, CallbackContext callbackContext) {
        CoapClient coapClient = buildClient(options);
        boolean ping = coapClient.ping(options.getTimeout());
        callbackContext.success(
                (new Boolean(ping)).toString()
        );
        return true;
    }

    private boolean discover(RequestOptions options, CallbackContext callbackContext) {
        Gson gson = new Gson();
        CoapClient coapClient = buildClient(options);
        Set<WebLink> weblinks = coapClient.discover();
        callbackContext.success(
                gson.toJson(weblinks)
        );
        return true;
    }

    private boolean get(RequestOptions options, CallbackContext callbackContext) {
        CoapClient coapClient = buildClient(options);
        if (options.getAccept() != "") {
            coapClient.get(getCoapHandler(callbackContext));
        } else {
            coapClient.get(getCoapHandler(callbackContext), MediaTypeRegistry.parse(options.getAccept()));
        }
        return true;
    }

    private boolean post(RequestOptions options, CallbackContext callbackContext) {
        CoapClient coapClient = buildClient(options);
        if (options.getAccept() != "") {
            coapClient.post(getCoapHandler(callbackContext), options.getPayload(), MediaTypeRegistry.parse("text/plain"));
        } else {
            coapClient.post(getCoapHandler(callbackContext), options.getPayload(), MediaTypeRegistry.parse("text/plain"), MediaTypeRegistry.parse(options.getAccept()));
        }
        return true;
    }

    private boolean put(RequestOptions options, CallbackContext callbackContext) {
        CoapClient coapClient = buildClient(options);
        coapClient.put(getCoapHandler(callbackContext), options.getPayload(), MediaTypeRegistry.parse("text/plain"));
        return true;
    }

    private boolean delete(RequestOptions options, CallbackContext callbackContext) {
        CoapClient coapClient = buildClient(options);
        coapClient.delete(getCoapHandler(callbackContext));
        return true;
    }

    private CoapHandler getCoapHandler(CallbackContext callbackContext) {
        Gson gson = new Gson();
        return new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                callbackContext.success(
                        gson.toJson(response)
                );
            }

            @Override
            public void onError() {
                callbackContext.error("Enpoint not available or timeout");
            }
        };
    }


    private CoapClient buildClient(RequestOptions options) {
        CoapClient coapClient = new CoapClient.Builder(options.getHost(), options.getPort())
                .scheme(options.getProtocol())
                .path(options.getPath())
                .query(options.getQuery())
                .create();
        coapClient.setTimeout(options.getTimeout());
        if (options.isUseCons()) {
            coapClient.useCONs();
        } else {
            coapClient.useNONs();
        }
        return coapClient;
    }

    private RequestOptions jsonObjectToRequestOptions(JSONObject obj) throws JSONException {
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

    class RequestOptions {
        private final int TIMEOUT = 1000;

        private String protocol;
        private String host;
        private int port;

        private String path;
        private String payload;
        private String query;
        private String accept;
        private boolean useCons;


        private int timeout;


        public RequestOptions(String protocol, String host, int port) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.payload = "";
            this.query = "";
            this.useCons = false;
            this.timeout = TIMEOUT;
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

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }
}
