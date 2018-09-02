package com.phodal.plugin;
import com.google.gson.Gson;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Coap extends CordovaPlugin {

    private static String TAG = "CoapClient";


    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        CordovaCoapClient coapClient = new CordovaCoapClient(callbackContext);
        Log.d(TAG, data.toString());
        RequestOptions options;
        try {
            JSONObject jsonObj = data.getJSONObject(0);
            options = RequestOptions.jsonObjectToRequestOptions(jsonObj);
        } catch (JSONException ex) {
            return false;
        }
        if (action.equals("get")) {
            Log.d(TAG, "GET");
            return coapClient.get(options);
        } else if(action.equals("post")) {
            Log.d(TAG, "POST");
            return coapClient.post(options);
        } else if(action.equals("put")) {
            Log.d(TAG, "PUT");
            return coapClient.put(options);
        } else if(action.equals("delete")) {
            Log.d(TAG, "DELETE");
            return coapClient.delete(options);
        } else if (action.equals("ping")) {
            Log.d(TAG, "PING");
            return coapClient.ping(options);
        } else if (action.equals("discover")) {
            Log.d(TAG, "DISCOVEr");
            return coapClient.discover(options);
        } else {
            Log.d(TAG, "Unknown");
            callbackContext.error("Unknown Action");
            return false;
        }
    }
}
