package me.noctambulist.aasweb.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/20 21:18
 */
class JsonUtilsTest {

    @Test
    public void testNewObjectNode() {
        JsonNode node = JsonUtils.newObjectNode();
        assertNotNull(node);
        assertTrue(node.isObject());
    }

    @Test
    public void testToJson() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");
        String json = JsonUtils.toJson(map);
        assertEquals("{\"foo\":\"bar\"}", json);
    }

    @Test
    public void testFromJson() throws Exception {
        String json = "{\"foo\":\"bar\"}";
        Map<String, String> map = JsonUtils.fromJson(json, new HashMap<String, String>().getClass());
        assertEquals(1, map.size());
        assertEquals("bar", map.get("foo"));
    }

    @Test
    public void testFromJsonToMap() throws IOException {
        String json = "{\"foo\":\"bar\"}";
        Map<String, Object> map = JsonUtils.fromJsonToMap(json);
        assertEquals(1, map.size());
        assertEquals("bar", map.get("foo"));
    }

    @Test
    public void testFromJsonToList() throws IOException {
        String json = "[{\"foo\":\"bar\"}]";
        List<Object> list = JsonUtils.fromJsonToList(json);
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof Map);
        assertEquals("bar", ((Map) list.get(0)).get("foo"));
    }

    @Test
    public void testToJsonNode() throws IOException {
        String json = "{\"foo\":\"bar\"}";
        String expectedJsonString = "{\"foo\":\"bar\"}";
        assertEquals(expectedJsonString, JsonUtils.toJsonNode(json).toString());
    }

}