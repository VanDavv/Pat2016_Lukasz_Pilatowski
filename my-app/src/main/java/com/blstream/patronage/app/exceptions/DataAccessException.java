package com.blstream.patronage.app.exceptions;

import javassist.NotFoundException;

/**
 * Created by VanDavv on 2016-01-26.
 */
public class DataAccessException extends NotFoundException {
    public DataAccessException(String msg) {
        super(msg);
    }
}
