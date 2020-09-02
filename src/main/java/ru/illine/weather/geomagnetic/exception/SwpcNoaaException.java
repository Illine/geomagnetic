package ru.illine.weather.geomagnetic.exception;

public class SwpcNoaaException extends RuntimeException {

    public SwpcNoaaException(String msg, Throwable ex) {
        super(msg, ex);
    }
}