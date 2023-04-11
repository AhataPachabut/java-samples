package com.epam.exception;

public class MissingBankCardException extends Exception {

    public MissingBankCardException(String bankCardNumber) {
        super("Missing bank card number: " + bankCardNumber);
    }
}
