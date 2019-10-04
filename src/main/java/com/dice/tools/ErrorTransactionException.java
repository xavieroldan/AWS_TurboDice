package com.dice.tools;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "No fue posible la transacción.")
public class ErrorTransactionException extends Exception
{

    public ErrorTransactionException(String msg)
    {
        super(msg);
    }

    public ErrorTransactionException()
    {
        super();
    }
}
