package com.phodal.plugin;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import java.util.Set;

import org.apache.cordova.*;

public class CordovaCoapClient {

    private CallbackContext callbackContext;

    public CordovaCoapClient(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    public boolean ping(RequestOptions options) {
        CoapClient coapClient = buildClient(options);
        boolean ping = coapClient.ping(options.getTimeout());
        callbackContext.success(new Boolean(ping).toString());
        return true;
    }

    public boolean discover(RequestOptions options) {
        Gson gson = new Gson();
        CoapClient coapClient = buildClient(options);
        Set<WebLink> weblinks = coapClient.discover();
        callbackContext.success(gson.toJson(weblinks));
        return true;
    }

    public boolean get(RequestOptions options) {
        CoapClient coapClient = buildClient(options);
        if (options.getAccept() != "") {
            coapClient.get(getCoapHandler());
        } else {
            coapClient.get(getCoapHandler(), MediaTypeRegistry.parse(options.getAccept()));
        }
        return true;
    }

    public boolean post(RequestOptions options) {
        CoapClient coapClient = buildClient(options);
        if (options.getAccept() != "") {
            coapClient.post(getCoapHandler(), options.getPayload(), MediaTypeRegistry.parse("text/plain"));
        } else {
            coapClient.post(getCoapHandler(), options.getPayload(), MediaTypeRegistry.parse("text/plain"), MediaTypeRegistry.parse(options.getAccept()));
        }
        return true;
    }

    public boolean put(RequestOptions options) {
        CoapClient coapClient = buildClient(options);
        coapClient.put(getCoapHandler(), options.getPayload(), MediaTypeRegistry.parse("text/plain"));
        return true;
    }

    public boolean delete(RequestOptions options) {
        CoapClient coapClient = buildClient(options);
        coapClient.delete(getCoapHandler());
        return true;
    }

    private CoapHandler getCoapHandler() {
        return new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                callbackContext.success(ResponseResult.coapResponseResult(response));
            }

            @Override
            public void onError() {
                callbackContext.error("Coap Request Error!");
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
}
