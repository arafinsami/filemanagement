package com.filemanagement.utils;

import lombok.Data;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseBuilder implements Response {

    private final ResponseType type;

    private Object data = null;

    private Object meta = null;

    private Object errors = null;

    private String message = null;

    public ResponseBuilder(ResponseType type) {
        this.type = type;
    }

    public static ResponseBuilder success(Object data) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.DATA);
        response.data = data;
        return response;
    }

    public static ResponseBuilder error(Object errors) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.ERROR);
        response.errors = errors;
        return response;
    }

    @Override
    public JSONObject getJson() {
        Map<String, Object> maps = new HashMap<String, Object>();
        switch (this.type) {
            case DATA:
                maps.put("data", data);
            case ERROR:
                maps.put("errors", errors);
        }
        return new JSONObject(maps);
    }

}