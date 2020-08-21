package com.spacestudent.ssapi.payload.rest;

public enum ResponseStatus {

    SUCCESS(0), FAILED(1), ABORTED(2), PENDING(3);

    private  int status;

    ResponseStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return  this.status;
    }
}
