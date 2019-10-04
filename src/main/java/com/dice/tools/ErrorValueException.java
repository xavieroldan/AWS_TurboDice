package com.dice.tools;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Valor introducido incorrecto.")
public class ErrorValueException extends Exception
{

    public ErrorValueException(String msg)
    {
        super(msg);
    }

    public ErrorValueException()
    {
        super();
    }
}
