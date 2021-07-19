package com.project0.dao;

import java.util.List;

import com.project0.exception.BusinessException;
import com.project0.model.Account;


public interface AccountDAO {
	public Account getAccountById(int accountId)throws BusinessException;
	public List<Account> getAccountByBal(double accountBal)throws BusinessException;
	public List<Account> getAccountByValid(boolean valid)throws BusinessException;
	public List<Account> getAccountByUserId(int userId)throws BusinessException;

	public Account createAccount(Account account) throws BusinessException;
	public Account updateAccount(int accountId,double accountBal) throws BusinessException;
	public Account updateAccount(int accountId,boolean valid) throws BusinessException;
	public List<Account> getAllAccounts() throws BusinessException;
	public void deleteAccount(int accountId) throws BusinessException;

}
