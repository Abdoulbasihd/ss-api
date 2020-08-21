package com.spacestudent.ssapi.payload.rest;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RestResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object data;
    private String message;
    private ResponseStatus status;
    private ResponseCode code;
    
    public RestResponse() {}

    public RestResponse(String message, ResponseStatus status, ResponseCode code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public RestResponse(Object data, String message, ResponseStatus status, ResponseCode code) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.code = code;
    }

}
