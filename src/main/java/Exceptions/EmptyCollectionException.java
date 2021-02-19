/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author ruidu
 */
public class EmptyCollectionException extends Exception {

    public EmptyCollectionException() {
    }

    public EmptyCollectionException(String message) {
        super(message);
    }

    public EmptyCollectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyCollectionException(Throwable cause) {
        super(cause);
    }

    public EmptyCollectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
