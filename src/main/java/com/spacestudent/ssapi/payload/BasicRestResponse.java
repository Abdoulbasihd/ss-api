package com.spacestudent.ssapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BasicRestResponse implements Serializable {

    private int status;
    private String message;
    private Object data;
}
