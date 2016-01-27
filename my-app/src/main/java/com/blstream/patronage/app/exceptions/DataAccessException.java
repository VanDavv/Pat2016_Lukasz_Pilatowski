package com.blstream.patronage.app.exceptions;

import javassist.NotFoundException;


public class DataAccessException extends NotFoundException {
    public DataAccessException(String msg) {
        super(msg);
    }
}
