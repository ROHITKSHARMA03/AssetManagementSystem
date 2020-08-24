package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.AmountTransfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.NotEnoughFundsException;
import com.db.awmd.challenge.exception.TransferBetweenSameAccountException;
import com.db.awmd.challenge.repository.AccountsRepository;

@Service
public class AccountsService {

	@Autowired
    private  AccountsRepository accountsRepository;

	public void setAccountsRepository(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	@Autowired
    private  NotificationService notificationService;

    @Autowired
    private AmountValidator amountValidator;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, NotificationService notificationService) {
        this.accountsRepository = accountsRepository;
        this.notificationService = notificationService;
    }

    public void createAccount(Account account) {
        this.accountsRepository.createAccount(account);
    }

   
	public Account getAccount(String accountId) {
        return this.accountsRepository.getAccount(accountId);
    }

   
    public void transferAmount(AmountTransfer transfer) throws AccountNotFoundException, NotEnoughFundsException,TransferBetweenSameAccountException {

        final Account accountFrom = accountsRepository.getAccount(transfer.getAccountFromId());
        final Account accountTo = accountsRepository.getAccount(transfer.getAccountToId());
        final BigDecimal amount = transfer.getAmount();

        amountValidator.validate(accountFrom, accountTo, transfer);

        //ideally atomic operation in production
        boolean successful = accountsRepository.updateAccount(accountFrom,accountTo,amount);

        if (successful){
            notificationService.notifyAboutTransfer(accountFrom, "Account Number " + accountTo.getAccountId() + " debited with amount " + transfer.getAmount() + ".");
            notificationService.notifyAboutTransfer(accountTo, "Account Number + " + accountFrom.getAccountId() + " credited with amount " + transfer.getAmount() + ".");
        }
    }
    
    public AccountsRepository getAccountsRepository() {
		return accountsRepository;
	}
    
    public AmountValidator getAmountValidator() {
		return amountValidator;
	}

	public void setAmountValidator(AmountValidator amountValidator) {
		this.amountValidator = amountValidator;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

   

}
