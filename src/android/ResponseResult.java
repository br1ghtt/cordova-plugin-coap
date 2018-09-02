package com.phodal.plugin;

import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ResponseResult {

    public static JSONObject coapResponseResult(CoapResponse response) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(ResponseKeys.ACKNOWLEDGED.toString(), response.advanced().isAcknowledged());
        jsonResponse.put(ResponseKeys.CANCELED.toString(), response.advanced().isCanceled());
        jsonResponse.put(ResponseKeys.CODE.toString(), response.getCode());
        jsonResponse.put(ResponseKeys.CODE_VALUE.toString(), response.getCode().toString());
        jsonResponse.put(ResponseKeys.DUPLICATE.toString(), response.advanced().isDuplicate());
        jsonResponse.put(ResponseKeys.MID.toString(), response.advanced().getMID());
        jsonResponse.put(ResponseKeys.PAYLOAD.toString(), response.getResponseText());
        jsonResponse.put(ResponseKeys.PAYLOAD_SIZE.toString(), response.advanced().getPayloadSize());
        jsonResponse.put(ResponseKeys.REJECTED.toString(), response.advanced().isRejected());
        jsonResponse.put(ResponseKeys.RTT.toString(), response.advanced().getRTT());
        jsonResponse.put(ResponseKeys.TIMED_OUT.toString(), response.advanced().isTimedOut());
        jsonResponse.put(ResponseKeys.TIMESTAMP.toString(), response.advanced().getTimestamp());
        jsonResponse.put(ResponseKeys.TYPE.toString(), response.advanced().getType());
        jsonResponse.put(ResponseKeys.TYPE_VALUE.toString(), response.advanced().getType().value);
        jsonResponse.put(ResponseKeys.TOKEN.toString(), "0x".concat(response.advanced().getTokenString()));
        addOptions(jsonResponse, response);
        addLayer3(jsonResponse, response);

        return jsonResponse;
    }

    private static void addOptions(JSONObject jsonResponse, CoapResponse response) {
        JSONObject optionsObject = new JSONObject();
        optionsObject.put(
                OptionsKeys.IF_MATCH.toString(),
                byteListToJsonArray(response.getOptions().getIfMatch())
        );
        optionsObject.put(OptionsKeys.URI_HOST.toString(), response.getOptions().getUriHost());
        optionsObject.put(
                OptionsKeys.ETAG.toString(),
                byteListToJsonArray(response.getOptions().getETags())
        );
        optionsObject.put(OptionsKeys.IF_NONE_MATCH.toString(), response.getOptions().hasIfNoneMatch());
        optionsObject.put(OptionsKeys.URI_PORT.toString(), response.getOptions().getUriPort());
        optionsObject.put(
                OptionsKeys.LOCATION_PATH.toString(),
                response.getOptions().getLocationPathString()
        );
        optionsObject.put(
                OptionsKeys.URI_PATH.toString(),
                stringListToJsonArray(response.getOptions().getUriPath()));

        optionsObject.put(OptionsKeys.URI_PATH.toString(), response.getOptions().getUriPathString());
        optionsObject.put(
                OptionsKeys.CONTENT_FORMAT.toString(),
                MediaTypeRegistry.toString(response.getOptions().getContentFormat())
        );
        optionsObject.put(
                OptionsKeys.CONTENT_FORMAT_VALUE.toString(),
                response.getOptions().getContentFormat()
        );
        optionsObject.put(OptionsKeys.MAX_AGE.toString(), response.getOptions().getMaxAge());
        optionsObject.put(OptionsKeys.URI_QUERY.toString(), response.getOptions().getUriQueryString());
        optionsObject.put(
                OptionsKeys.ACCEPT.toString(),
                MediaTypeRegistry.toString(response.getOptions().getAccept())
        );
        optionsObject.put(OptionsKeys.ACCEPT_VALUE.toString(), response.getOptions().getAccept());
        optionsObject.put(
                OptionsKeys.LOCATION_QUERY.toString(),
                response.getOptions().getLocationQueryString()
        );
        optionsObject.put(OptionsKeys.PROXY_URI.toString(), response.getOptions().getProxyUri());
        optionsObject.put(OptionsKeys.PROXY_SCHEME.toString(), response.getOptions().getProxyScheme());
        optionsObject.put(OptionsKeys.SIZE1.toString(), response.getOptions().getSize1());
        optionsObject.put(OptionsKeys.SIZE2.toString(), response.getOptions().getSize2());

        jsonResponse.put(ResponseKeys.OPTIONS.toString(), optionsObject);
    }

    private static JSONArray byteListToJsonArray(List<byte[]> byteList) {
        JSONArray jsonArray = new JSONArray();
        for (byte[] byteElement : byteList) {
            jsonArray.put(DataTypeConverter.bytesToHexString(byteElement));
        }
        return jsonArray;
    }

    private static JSONArray stringListToJsonArray(List<String> strings) {
        JSONArray jsonArray = new JSONArray();
        for (String string : strings) {
            jsonArray.put(string);
        }
        return jsonArray;
    }

    private static void addLayer3(JSONObject jsonResponse, CoapResponse response) {
        JSONObject layer3Object = new JSONObject();
        if (response.advanced().getDestination() != null) {
            layer3Object.put(Layer3Keys.DESTINATION_ADDRESS.toString(), response.advanced().getDestination().getHostAddress());
        } else {
            layer3Object.put(Layer3Keys.DESTINATION_ADDRESS.toString(), "");
        }
        layer3Object.put(Layer3Keys.DESTINATION_PORT.toString(), response.advanced().getDestinationPort());
        if (response.advanced().getSource() != null) {
            layer3Object.put(Layer3Keys.SOURCE_ADDRESS.toString(), response.advanced().getSource());
        } else {
            layer3Object.put(Layer3Keys.SOURCE_ADDRESS.toString(), "");
        }
        layer3Object.put(Layer3Keys.SOURCE_PORT.toString(), response.advanced().getSourcePort());
        jsonResponse.put(ResponseKeys.LAYER3.toString(), layer3Object);
    }

    protected enum ResponseKeys {
        ACKNOWLEDGED("acknowledged"),
        CANCELED("canceled"),
        CODE("code"), CODE_VALUE("code_value"),
        DUPLICATE("duplicated"),
        MID("mid"),
        REJECTED("rejected"),
        RTT("rtt"),
        TIMED_OUT("timed_out"),
        TIMESTAMP("timestamp"),
        TOKEN("token"),
        TYPE("type"), TYPE_VALUE("type_value"),
        PAYLOAD("payload"), PAYLOAD_SIZE("payload_size"),
        LAYER3("layer3"),
        OPTIONS("options");

        private final String stringKey;

        /**
         * @param key
         */
        ResponseKeys(final String key) {
            this.stringKey = key;
        }

        @Override
        public String toString() {
            return this.stringKey;
        }
    }

    protected enum OptionsKeys {
        IF_MATCH("if_match"),
        URI_HOST("uri_host"),
        ETAG("etag"),
        IF_NONE_MATCH("if_none_match"),
        URI_PORT("uri_port"),
        LOCATION_PATH("location_path"),
        URI_PATH("uri_path"),
        CONTENT_FORMAT("content_format"),
        CONTENT_FORMAT_VALUE("content_format_value"),
        MAX_AGE("max_age"),
        URI_QUERY("uri_query"),
        ACCEPT("accept"),
        ACCEPT_VALUE("accept_value"),
        LOCATION_QUERY("location_query"),
        PROXY_URI("proxy_uri"),
        PROXY_SCHEME("proxy_scheme"),
        SIZE1("size1"),
        SIZE2("size2");

        private final String stringKey;

        /**
         * @param key
         */
        OptionsKeys(final String key) {
            this.stringKey = key;
        }

        @Override
        public String toString() {
            return this.stringKey;
        }
    }

    protected enum Layer3Keys {
        DESTINATION_ADDRESS("destination_address"),
        DESTINATION_PORT("destination_port"),
        SOURCE_ADDRESS("source_address"),
        SOURCE_PORT("source_port");


        private final String stringKey;

        /**
         * @param key
         */
        Layer3Keys(final String key) {
            this.stringKey = key;
        }

        @Override
        public String toString() {
            return this.stringKey;
        }
    }

}
