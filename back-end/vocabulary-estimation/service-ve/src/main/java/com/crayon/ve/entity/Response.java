package com.crayon.ve.entity;

import com.crayon.ve.constant.enumeration.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "Response对象", description = "响应数据的实体类")
public class Response {
    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("返回代码")
    private String code;
    @ApiModelProperty("详细描述信息")
    private String description;

    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>();

    private Response() {
    }

    public static Response getResponse(boolean success) {
        return success ? success() : fail();
    }

    public static Response getResponse(StatusEnum statusEnum) {
        Response resp = new Response();
        resp.setSuccess(Objects.equals(statusEnum, StatusEnum.SUCCESS));
        resp.setStatusEnum(statusEnum);
        return resp;
    }

    public static Response success() {
        Response resp = new Response();
        resp.setSuccess(true);
        resp.setStatusEnum(StatusEnum.SUCCESS);

        return resp;
    }

    public static Response fail() {
        Response resp = new Response();
        resp.setSuccess(false);
        resp.setStatusEnum(StatusEnum.FAIL);

        return resp;
    }

    public Response setStatusEnum(StatusEnum statusEnum) {
        this.code = statusEnum.getCode();
        this.description = statusEnum.getDescription();
        return this;
    }

    public Response putItem(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Response removeItem(String key) {
        this.data.remove(key);
        return this;
    }

    public Response appenddescription(Object description) {
        this.description = this.description + "; " + description;
        return this;
    }
}
