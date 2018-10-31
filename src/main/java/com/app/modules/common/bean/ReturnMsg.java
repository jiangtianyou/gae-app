package com.app.modules.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnMsg {
    public enum Response {
        APP_OK(0, "处理成功"),
        APP_NG(1, "处理错误");

        private Integer code;
        private String message;

        private Response(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
      return code;
    }

        public String getMessage() {
      return message;
    }
    }

    private Integer code;
    private String msg;
    private Object data;

    public ReturnMsg() {

    }

    public ReturnMsg(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ReturnMsg(Response response) {
        this.code = response.getCode();
        this.msg = response.getMessage();
    }

    public ReturnMsg(Response response, Object data) {
        this(response);
        this.data = data;
    }

    public Integer getCode() {
    return code;
  }


    public void setCode(Integer code) {
    this.code = code;
  }


    public String getMsg() {
    return msg;
  }


    public void setMsg(String msg) {
    this.msg = msg;
  }


    public Object getData() {
    return data;
  }


    public void setData(Object data) {
    this.data = data;
  }

}
