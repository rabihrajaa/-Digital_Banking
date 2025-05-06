package org.sid.digitalbanking_backend.exceptions;

public class BankAccountNotSufficientException extends Exception {
    public BankAccountNotSufficientException(String message) {
    super(message);
    }
}
