package com.db.awmd.challenge.service;

import org.springframework.stereotype.Component;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.AmountTransfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.NotEnoughFundsException;

@Component
interface AmountValidator {

    void validate(final Account accountFrom, final Account accountTo, final AmountTransfer transfer) throws AccountNotFoundException, NotEnoughFundsException;

}
