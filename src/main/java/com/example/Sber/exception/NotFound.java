package com.example.Sber.exception;

public class NotFound extends RuntimeException{
    public NotFound() {
        super("Oбъект не был найден");
    }
}
