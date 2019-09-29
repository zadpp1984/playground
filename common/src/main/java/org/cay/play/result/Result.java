package org.cay.play.result;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.cay.play.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result<T> {


    private Integer code = 200;
    private String msg = "操作成功";
    private String description;
    private T data;

    private HashMap<String, Object> extend;

    public Result() {
        extend = new HashMap<>();
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public Result put(String key, Object value) {
        extend.put(key, value);
        return this;
    }

    public static Result failure(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.put("msg", msg);
        return result;
    }

    public static Result ok(Map<String, Object> map) {
        Result result = new Result();
        result.extend.putAll(map);
        return result;
    }

    public static Result ok() {
        return new Result();
    }

    public String toJsonString() throws JsonProcessingException {
        return JsonUtils.getJsonStringFromObject(this);
    }

}
