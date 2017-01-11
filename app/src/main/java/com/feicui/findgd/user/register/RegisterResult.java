package com.feicui.findgd.user.register;

/**
 * Created by LINS on 2017/1/10.
 * Please Try Hard
 */

import com.google.gson.annotations.SerializedName;

/**
 * 注册的响应结果实体
 */
public class RegisterResult {
    @SerializedName("tokenid")
    private int tokenId;

    @SerializedName("errcode")
    private int code;

    @SerializedName("errmsg")
    private String msg;

    public int getTokenId() {
        return tokenId;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
