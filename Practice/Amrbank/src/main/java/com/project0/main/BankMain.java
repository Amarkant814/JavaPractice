package com.project0.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.project0.exception.BusinessException;
import com.project0.model.Account;
import com.project0.model.Transaction;
import com.project0.model.User;
import com.project0.service.AccountService;
import com.project0.service.TransactionService;
import com.project0.service.UserService;
import com.project0.service.impl.AccountServiceImpl;
import com.project0.service.impl.TransactionServiceImpl;
import com.project0.service.impl.UserServiceImpl;

public class BankMain {

	private static Logger log = Logger.getLogger(BankMain.class);

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws BusinessException {

		int ch = 0;
		do {
			log.info("Welcome to AMR Bank V1.0");
			log.info("==================================");
			log.info("\n");
			log.info("1)Login");
			log.info("2)Register");
			log.info("3)EXIT");
			log.info("Enter your Choice 1-3");
			try {
				ch = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {

			}
			switch (ch) {
			case 1:
				displayLogin();
				break;

			case 2:
				register();
				break;
			case 3:
				log.info("Thanks for showing interest in our App.. See you soon. :)");
				break;

			default:
				log.warn("Invalid Choice... Please enter input between 1-3");
				break;
			}
		} while (ch != 3);

	}

	public static void displayLogin() throws BusinessException {
		int ch = 0;
		do {
			log.info("Login Options ");
			log.info("---------------");
			log.info(" ");
			log.info("1)Login as a customer");
			log.info("2)Log in as an employee");
			log.info("3)Return to main menu");
			log.info("Enter your Choice 1-3");
			try {
				ch = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {

			}
			switch (ch) {
			case 1:
				userLogin();
				break;
			case 2:
				empLogin();
				break;
			case 3:
				log.info("Return to main menu");

				break;

			default:
				log.warn("Invalid Choice... Please enter input between 1-3");
				break;
			}
		} while (ch != 3);
	}

	public static void register() {

		User user = new User();
		UserService userservice = new UserServiceImpl();
		log.info("Enter Username: ");
		user.setUserName(sc.nextLine());

		log.info("Enter Password: ");
		user.setPassword(sc.nextLine());

		try {
			user = userservice.createUser(user);
		} catch (BusinessException e) {
			log.error(e);
		}
	}

	public static void userLogin() throws BusinessException {
		UserService userservice = new UserServiceImpl();
		log.info("Enter username: ");
		String user_mail = sc.nextLine();
		log.info("Enter your password: ");
		String user_pass = sc.nextLine();
		User user = new User();
		user = userservice.getUserByName(user_mail);
		try {
			if (user.getPassword().equals(user_pass)) {
				log.info("Login Successful\n\n\n");
				log.info("Welcome " + user.getUserName());
				log.info("--------------");
				int ch = 0;
				do {
					log.info("1)Open New Account");
					log.info("2)Withdraw");
					log.info("3)Deposit");
					log.info("4)Transfer Money");
					log.info("5)View Transactions");
					log.info("6)View Balance");
					log.info("7)Log Out");
					log.info("Enter your Choice 1-7");
					try {
						ch = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {

					}
					switch (ch) {
					case 1:
						openAccount(user.getUserId());
						break;

					case 2:
						withdraw(user);
						break;

					case 3:
						deposit(user);
						break;

					case 4:
						transfer(user);
						break;
					case 5:
						viewTransactions(user);
						break;
						
					case 6:
						viewBalance(user);
						break;

					case 7:
						log.info("Returning to Login Menu");
						log.info("--------------");
						break;

					default:
						log.warn("Invalid Choice... Please enter input between 1-7");
						break;
					}
				} while (ch != 7);

			}
		} catch (NullPointerException e) {
			log.info("Please try again !!\n\n\n");
		}
	}

	public static void empLogin() {
		String emp_mail = "employee@amr.com";
		String pass = "secured ";
		int trial = 3;
		while (trial > 0) {
			log.info("Enter employee email id: ");
			String enteredValue = sc.nextLine();
			log.info("Enter employee password: ");
			String enteredPassword = sc.nextLine();
			trial--;
			if (emp_mail.equals(enteredValue) && enteredPassword.equals(pass)) {
				log.info("Employee Login Successfull !!!\n\n");
				log.info("******************");
				int ch = 0;
				do {
					log.info("         ------          ");
					log.info("1)Open New Customer Account");
					log.info("2)View Customer's Account");
					log.info("3)View Account Transactions");
					log.info("4)View Users");
					log.info("5)Approve or Reject Account");
					log.info("6)Log Out");
					log.info("Enter your Choice 1-6");
					try {
						ch = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {

					}
					switch (ch) {
					case 1:
						openAccount(0);
						break;

					case 2:
						viewCustomerAccount();
						break;
					
					case 3:
						viewAccountTransactions();
						break;

					case 4:
						viewUsers();
						break;
						
					case 5:
						approve();
						break;

					case 6:
						log.info("Returning to Login Menu");
						log.info("--------------");
						break;

					default:
						log.warn("Invalid Choice... Please enter input between 1-6");
						break;
					}
				} while (ch != 6);
				break;
			} else {
				log.info("Invalid credentials!!");
				log.info(trial + " Attempts left");
			}
		}
	}

	public static void openAccount(int userId) {

		Account account = new Account();
		account.setUserId(userId);
		log.info("Enter starting amount: ");
		// if amount < 0
		account.setAccountBal(sc.nextDouble());
		AccountService accountService = new AccountServiceImpl();
		try {
			account = accountService.createAccount(account);

		} catch (BusinessException e) {
			log.error(e);
		}
		log.info("**** Account created successfully ***");
		String type = "Credit";
		updateTransaction(type, account.getAccountBal(), account.getAccountId());
		// updatetransactionTable()
	}

	public static void withdraw(User user) {
		double withdrawal = 0;
		AccountService accountService = new AccountServiceImpl();
		List<Account> accountList = new ArrayList<>();
		try {
			accountList = accountService.getAccountByUserId(user.getUserId());
		} catch (BusinessException e) {
			log.error(e);
		}
		log.info("Select your account:");
		for (int i = 0; i < accountList.size(); i++) {
			log.info(i + ". " + accountList.get(i));
		}
		int index = 1;
		try {
			index = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {

		}

		Account account = accountList.get(index);
		log.info("Enter amount to withdraw:");
		withdrawal = sc.nextDouble();
		if (account.getAccountBal() < withdrawal ) {
			log.error("Insufficient Balance.. ");
		} 
		else if(withdrawal<0) {
			log.info("Please enter positive value");
		}
		else {
			account.setAccountBal(account.getAccountBal() - withdrawal);
			log.info("Transaction Successfull");
			try {
				account = accountService.updateAccount(account.getAccountId(), account.getAccountBal());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
			updateTransaction("Debit", withdrawal, account.getAccountId());
		}
	}

	public static void updateTransaction(String type, double amount, int accountId) {
		TransactionService transactionService = new TransactionServiceImpl();
		Transaction transaction = new Transaction();
		transaction.setType(type);
		transaction.setTransactionAmount(amount);
		transaction.setAccountId(accountId);
		try {
			transactionService.createTransaction(transaction);
		} catch (BusinessException e) {
			log.error(e);
		}
	}

	public static void deposit(User user) {
		double depo = 0;
		AccountService accountService = new AccountServiceImpl();
		List<Account> accountList = new ArrayList<>();
		try {
			accountList = accountService.getAccountByUserId(user.getUserId());
		} catch (BusinessException e) {
			log.error(e);
		}
		log.info("Select your account:");
		for (int i = 0; i < accountList.size(); i++) {
			log.info(i + ". " + accountList.get(i));
		}
		int index = 1;
		try {
			index = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {

		}

		Account account = accountList.get(index);
		log.info("Enter amount to deposit:");
		depo = sc.nextDouble();
		if (depo < 0) {
			log.error("Invalid amount");
		} else {
			account.setAccountBal(account.getAccountBal() + depo);
			log.info("Transaction Successfull");
			try {
				account = accountService.updateAccount(account.getAccountId(), account.getAccountBal());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
			updateTransaction("Credit", depo, account.getAccountId());
		}
	}

	public static void transfer(User user) {
		double depo = 0;
		AccountService accountService = new AccountServiceImpl();
		List<Account> accountList = new ArrayList<>();
		try {
			accountList = accountService.getAccountByUserId(user.getUserId());
		} catch (BusinessException e) {
			log.error(e);
		}
		log.info("Select your account:");
		for (int i = 0; i < accountList.size(); i++) {
			log.info(i + ". " + accountList.get(i));
		}
		int index = 1;
		try {
			index = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {

		}
		Account transferAccount = new Account();
		Account account = accountList.get(index);
		log.info("Enter amount to transfer:");
		depo = sc.nextDouble();
		log.info("Enter the recipient's account number:");
		transferAccount.setAccountId(sc.nextInt());

		try {
			transferAccount = accountService.getAccountById(transferAccount.getAccountId());
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			log.error("Invalid account Entered.. ");
		}

		if (depo < 0 || account.getAccountBal() < depo) {
			log.error("Transaction cannot be processed");
		} else {
			transferAccount.setAccountBal(transferAccount.getAccountBal() + depo);
			account.setAccountBal(account.getAccountBal() - depo);
			log.info("Transaction Successfull");
			try {
				transferAccount = accountService.updateAccount(transferAccount.getAccountId(),
						transferAccount.getAccountBal());
				account = accountService.updateAccount(account.getAccountId(), account.getAccountBal());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
			updateTransaction("Credit", depo, transferAccount.getAccountId());
			updateTransaction("Debit", depo, account.getAccountId());
		}
	}

	public static void viewTransactions(User user) {
		List<Transaction> transactionList = new ArrayList<>();
		TransactionService ts = new TransactionServiceImpl();

		AccountService accountService = new AccountServiceImpl();
		List<Account> accountList = new ArrayList<>();
		try {
			accountList = accountService.getAccountByUserId(user.getUserId());
		} catch (BusinessException e) {
			log.error(e);
		}
		log.info("Select your account:");
		for (int i = 0; i < accountList.size(); i++) {
			log.info(i + ". " + accountList.get(i));
		}
		int index = 1;
		try {
			index = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {

		}

		Account account = accountList.get(index);
		try {
			transactionList = ts.getTransactionByAccountId(account.getAccountId());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}

		for (Transaction i : transactionList) {
			log.info(i);
		}

	}

	public static void viewBalance(User user) {
		AccountService accountService = new AccountServiceImpl();
		List<Account> accountList = new ArrayList<>();
		try {
			accountList = accountService.getAccountByUserId(user.getUserId());
		} catch (BusinessException e) {
			log.error(e);
		}
		log.info("Select your account:");
		for (int i = 0; i < accountList.size(); i++) {
			log.info(i + ". " + accountList.get(i));
		}
		int index = 1;
		try {
			index = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {

		}

		Account account = accountList.get(index);
		log.info("Your account balance for the selected account is "+account.getAccountBal());
	}

	public static void approve() {
		viewCustomerAccount();
		log.info("Enter the account id you want to delete:");
		int accountId = Integer.parseInt(sc.nextLine());
		TransactionService ts = new TransactionServiceImpl();
		try {
			ts.deleteTransaction(accountId);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		AccountService accountService = new AccountServiceImpl();
		try {
			accountService.deleteAccount(accountId);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		log.info("Account deleted successfully");
		
	}
	
	public static void viewCustomerAccount() {
		AccountService accountService = new AccountServiceImpl();
		List<Account> accountList= new ArrayList<>();
		try {
			accountList = accountService.getAllAccounts();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		for(Account acc : accountList) {
			log.info(acc);
		}
	}
	
	public static void viewUsers() {
		List<User> userList = new ArrayList<>();
		UserService userservice = new UserServiceImpl();
		try {
			userList = userservice.getAllUsers();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		for(User usr: userList) {
			log.info(usr);
		}
	}

	public static void viewAccountTransactions() {
		List<Transaction> transactionList = new ArrayList<>();
		TransactionService ts = new TransactionServiceImpl();
		try {
			transactionList = ts.getAllTransactions();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		for(Transaction tsList : transactionList) {
			log.info(tsList);
		}
	}
}
