package com.itbd.energymanager.utiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ridoy.kumar <br>
 * Date: 11-12-2021
 */

@Slf4j
public class JsonUtil {

    public static <T> T getJsonNodeToObject(JsonNode arrayNode) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<T>() {
        });
        try {
            return reader.readValue(arrayNode);
        } catch (IOException e) {
            log.error("Can't convert Json to Object: [{}]", e.toString());
        }
        return null;
    }

    public static <T> T getJsonStringToObj(Class<T> t, String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(jsonString, t);
        } catch (JsonProcessingException e) {
            log.error("Can't convert Json string to Object: [{}]", e.toString());
        }
        return null;
    }

    private static List<String> getObjectFields(JsonNode rootNode) {
        List<String> actualList = new ArrayList<>();
        rootNode.fieldNames().forEachRemaining(actualList::add);
        return actualList;
    }

    public static <T> JsonNode getObjToJsonNode(List<T> t, List<String> fields) {
        JsonNode rootNode = getStringToJsonNode(getObjToJsonString(t));
        Iterator<JsonNode> jNodeItr = rootNode.elements();
        if (fields.isEmpty()) {
            return rootNode;
        }
        while (jNodeItr.hasNext()) {
            JsonNode node = jNodeItr.next();
            ObjectNode object = (ObjectNode) node;
            object.remove(getUnnecessaryFields(getObjectFields(node), fields));
        }
        return rootNode;
    }

    public static <T> JsonNode getObjToJsonNode(T t) {
        return getStringToJsonNode(getObjToJsonString(t));
    }

    public static <T> String getObjToJsonString(T t) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("Can't convert Object to String: [{}]", e.toString());
        }
        return null;
    }

    public static JsonNode getStringToJsonNode(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            log.error("Can't parse Json string to jsonNode: [{}]", e.toString());
        }
        return null;
    }

    private static List<String> getUnnecessaryFields(List<String> fieldList, List<String> necessaryFields) {
        fieldList.removeAll(necessaryFields);
        return fieldList;
    }


}
