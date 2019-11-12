package com.inerpapi.exception;

public class ErrorMessages {

    private String msgDev;

    private String msgClient;

    public ErrorMessages(String msgDev, String msgClient) {
        this.msgClient = msgClient;
        this.msgDev = msgDev;
    }

    public String getMsgDev() {
        return msgDev;
    }

    public String getMsgClient() {
        return msgClient;
    }
}