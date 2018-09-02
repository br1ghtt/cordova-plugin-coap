package com.phodal.plugin;

import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

public class MediaTypeResult {

    public static JSONArray getAllMediaTypes() {
        JSONArray mediaTypeArray = new JSONArray("[]");
        Set<Integer> mediaTypes = MediaTypeRegistry.getAllMediaTypes();
        for (Integer mediaType : mediaTypes) {
            JSONObject mediaTypeObj = new JSONObject();
            mediaTypeObj.put(
                    MediaTypeKeys.CODE.toString(),
                    mediaType
            );
            mediaTypeObj.put(
                    MediaTypeKeys.VALUE.toString(),
                    MediaTypeRegistry.toString(mediaType)
            );
            mediaTypeArray.put(mediaTypeObj);
        }
        return mediaTypeArray;
    }

    protected enum MediaTypeKeys {
        CODE("code"), VALUE("value");

        private final String mediaTypeKey;

        /**
         * @param key
         */
        MediaTypeKeys(final String key) {
            this.mediaTypeKey = key;
        }

        @Override
        public String toString() {
            return this.mediaTypeKey;
        }
    }
}
