package com.project0.dao;

import java.util.List;

import com.project0.exception.BusinessException;
import com.project0.model.Transaction;

public interface TransactionDAO {
	
	public Transaction getTransactionById(int transactionId)throws BusinessException;
	public List<Transaction> getTransactionByType(String type)throws BusinessException;
	public List<Transaction> getTransactionByAccountId(int accountId)throws BusinessException;

	public Transaction createTransaction(Transaction transaction) throws BusinessException;
	public Transaction updateTransaction(int transactionId,String type) throws BusinessException;
	public List<Transaction> getAllTransactions() throws BusinessException;
	public void deleteTransaction(int transactionId) throws BusinessException;

}
