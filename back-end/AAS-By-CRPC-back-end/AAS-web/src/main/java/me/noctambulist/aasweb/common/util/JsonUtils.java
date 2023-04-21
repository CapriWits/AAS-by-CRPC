package me.noctambulist.aasweb.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/20 19:58
 */
public class JsonUtils {

    /**
     * Set empty bean without throwing an exception
     */
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    public static ObjectNode newObjectNode() {
        return mapper.createObjectNode();
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> type) throws IOException {
        return mapper.readValue(json, type);
    }

    public static Map<String, Object> fromJsonToMap(String json) throws IOException {
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        return mapper.readValue(json, typeRef);
    }

    public static List<Object> fromJsonToList(String json) throws IOException {
        TypeReference<List<Object>> typeRef = new TypeReference<List<Object>>() {
        };
        return mapper.readValue(json, typeRef);
    }

    public static JsonNode toJsonNode(String json) throws IOException {
        return mapper.readTree(json);
    }

}
