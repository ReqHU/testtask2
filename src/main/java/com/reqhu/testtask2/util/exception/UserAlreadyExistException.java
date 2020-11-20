package com.reqhu.testtask2.util.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException() {
        super("пользователь уже существует");
    }

}
