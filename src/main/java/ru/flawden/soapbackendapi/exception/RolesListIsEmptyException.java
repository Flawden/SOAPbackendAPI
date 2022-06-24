package ru.flawden.soapbackendapi.exception;

public class RolesListIsEmptyException extends RuntimeException{

    public RolesListIsEmptyException() {super();}
    public RolesListIsEmptyException(String message) {
        super(message);
    }

}
