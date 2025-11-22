package cn.kaimier.reggie.common;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}