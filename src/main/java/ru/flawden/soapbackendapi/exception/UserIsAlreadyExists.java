package ru.flawden.soapbackendapi.exception;

public class UserIsAlreadyExists extends RuntimeException {

    public UserIsAlreadyExists() {super();}
    public UserIsAlreadyExists(String message) {
        super(message);
    }

}
