package com.spacestudent.ssapi.payload.rest;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseCode {

    TRANSACTION_SUCCESSFUL(200, "Transaction successful"),
    ACCOUNT_CREATED(201, "ACCOUNT CREATED"),

    SELECTED_GROUP_NOT_EXIST(410, "SELECTED GROUP(EXAMINATION LEVEL) DOESN'T EXIST"),
    USERNAME_ALREADY_USED(411, "USERNAME ALREADY USED"),
    SEND_DATA_NULL_FOR_ACCOUNT_CREATION(412, "BAD REQUEST, NULL DATA"),
    INVALID_USERNAME_OR_PASSWORD(413, "Username Or Password Invalid"),

    ERROR_WHILE_SAVING_DATA(510, "ERROR WHILE SAVING DATA");

    @JsonValue
    private int code;

    private String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ResponseCode evalFromInt(int statusCode) {
        ResponseCode status = resolve(statusCode);
        if (status == null) {
            return resolve(406);
        } else {
            return status;
        }
    }

    public static ResponseCode resolve(int statusCode) {
        ResponseCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResponseCode status = var1[var3];
            if (status.code == statusCode) {
                return status;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "" + this.getCode();
    }
}
