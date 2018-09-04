package com.phodal.plugin;

import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.server.resources.ResourceAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

public class ResourceResult {

    public static JSONArray coapResourceResult(Set<WebLink> webLinkSet) {
        JSONArray jsonResources = new JSONArray();

        for (WebLink weblink: webLinkSet) {
            JSONObject jsonResource = new JSONObject();
            jsonResource.put(ResourceKeys.URI.toString(), weblink.getURI());
            ResourceAttributes attributes = weblink.getAttributes();
            jsonResource.put(
              ResourceKeys.ATTRIBUTES.toString(),
              coapAttributeArray(attributes)
            );
            jsonResources.put(jsonResource);
        }
        return jsonResources;
    }

    private static JSONArray coapAttributeArray(ResourceAttributes attributes) {
        JSONArray jsonAttributes = new JSONArray();
        for (String attributeKey: attributes.getAttributeKeySet()) {
            JSONObject jsonAttribute = new JSONObject();
            jsonAttribute.put(AttributeKeys.KEY.toString(), attributeKey);
            JSONArray jsonAttributeValues = new JSONArray();
            for (String attributeValue: attributes.getAttributeValues(attributeKey)) {
                JSONObject jsonAttributeValue = new JSONObject();
                jsonAttributeValue.put(AttributeValueKeys.VALUE.toString(), attributeValue);
                jsonAttributeValues.put(jsonAttributeValue);
            }
            jsonAttribute.put(AttributeKeys.VALUES.toString(), jsonAttributeValues);
            jsonAttributes.put(jsonAttribute);
        }
        return jsonAttributes;

    }

    protected enum ResourceKeys {

        URI("uri"),
        ATTRIBUTES("attributes");

        private final String stringKey;

        /**
         * @param key
         */
        ResourceKeys(final String key) {
            this.stringKey = key;
        }

        @Override
        public String toString() {
            return this.stringKey;
        }
    }
    protected enum AttributeKeys {

        KEY("key"), VALUES("values");

        private final String stringKey;

        /**
         * @param key
         */
        AttributeKeys(final String key) {
            this.stringKey = key;
        }

        @Override
        public String toString() {
            return this.stringKey;
        }
    }

    protected enum AttributeValueKeys {

        VALUE("value");

        private final String stringKey;

        /**
         * @param key
         */
        AttributeValueKeys(final String key) {
            this.stringKey = key;
        }

        @Override
        public String toString() {
            return this.stringKey;
        }
    }
}
