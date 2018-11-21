package com.cg.wallet.service;

import com.cg.wallet.bean.AccountBean;
import com.cg.wallet.exception.CustomerException;


public interface IWalletService {
	 public boolean createAccount(AccountBean accountBean) throws  CustomerException ;
     public AccountBean showBalance(int accountId) throws Exception;
     public boolean deposit(AccountBean accountBean,double depositAmount) throws Exception ;
     public boolean withdraw(AccountBean accountBean,double withdrawAmount) throws Exception;
     public boolean fundTransfer(AccountBean transferingAccountBean, AccountBean beneficiaryAccountBean, double transferAmount) throws Exception ;
       

}
