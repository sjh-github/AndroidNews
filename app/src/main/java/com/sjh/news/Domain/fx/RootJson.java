package com.sjh.news.Domain.fx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 25235 on 2018/7/14.
 */

public class RootJson {
    private boolean success;
    private String code;
    private String msg;
    private String requestId;
    @SerializedName("data")
    private DataJson dataJson;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public DataJson getDataJson() {
        return dataJson;
    }

    public void setDataJson(DataJson dataJson) {
        this.dataJson = dataJson;
    }

    @Override
    public String toString() {
        return "RootJson{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", requestId='" + requestId + '\'' +
                ", dataJson=" + dataJson +
                '}';
    }
}
