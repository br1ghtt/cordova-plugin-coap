package com.phodal.plugin;

import com.google.gson.Gson;
import org.apache.cordova.*;
import org.eclipse.californium.core.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class Coap extends CordovaPlugin {

    private static int TIMEOUT = 1000;
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
            Log.d(TAG, "\n test for get");

            try {
                URI uri = new URI(data.getString(0));
                CoapClient mCoapClient = new CoapClient(uri);
                CoapResponse response = mCoapClient.get();
                callbackContext.success(response.getResponseText());
                return true;
            } catch (URISyntaxException e) {
                Log.e(TAG, "URISyntaxException");
                callbackContext.error("URISyntaxException");
                return false;
            }
        } else if(action.equals("post")) {
            Log.d(TAG, "\n test for post");

            try {
                URI uri = new URI(data.getString(0));
                CoapClient mCoapClient = new CoapClient(uri);
                CoapResponse response = mCoapClient.post(data.getString(1), 0);
                callbackContext.success(response.getResponseText());
                return true;
            } catch (URISyntaxException e) {
                Log.e(TAG, "URISyntaxException");
                callbackContext.error("URISyntaxException");
                return false;
            } catch (Exception e) {
                Log.e(TAG, "Exception");
                callbackContext.error("Exception");
                return false;
            }
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
        boolean ping = coapClient.ping(TIMEOUT);
        callbackContext.success(
                (new Boolean(ping)).toString()
        );
        return true;
    }

    private boolean discover(RequestOptions options, CallbackContext callbackContext) {
        CoapClient coapClient = buildClient(options);
        Set<WebLink> weblinks = coapClient.discover();
        callbackContext.success(
                gson.toJson(weblinks)
        );
        return true;
    }


    private CoapClient buildClient(RequestOptions options) {
        return new CoapClient(
                options.getProtocol(),
                options.getHost(),
                options.getPort());
    }

    private RequestOptions jsonObjectToRequestOptions(JSONObject obj) throws JSONException {
        RequestOptions options = new RequestOptions(
                obj.getString("protocol"),
                obj.getString("host"),
                obj.getInt("port")
        );
        if (!obj.isNull("payload")) options.setPayload(obj.getString("payload"));
        return options;
    }

    class RequestOptions {
        private String protocol;
        private String host;
        private int port;

        public String payload;

        public RequestOptions(String protocol, String host, int port) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
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

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }

    }
}
