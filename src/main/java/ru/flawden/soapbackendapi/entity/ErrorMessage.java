package ru.flawden.soapbackendapi.entity;

public class ErrorMessage {

    public ErrorMessage(String error) {
        this.error = error;
    }

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
