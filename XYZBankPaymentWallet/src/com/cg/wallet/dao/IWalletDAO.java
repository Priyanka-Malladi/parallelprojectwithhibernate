package com.cg.wallet.dao;

import com.cg.wallet.bean.AccountBean;
import com.cg.wallet.exception.CustomerException;

public interface IWalletDAO {
	
	 public boolean createAccount(AccountBean accountBean) throws  CustomerException ;
     public boolean deposit(AccountBean accountBean,double depositAmount) throws Exception ;
     public boolean withdraw(AccountBean accountBean,double withdrawAmount) throws Exception;
     public boolean fundTransfer(AccountBean transferingAccountBean, AccountBean beneficiaryAccountBean, double transferAmount) throws Exception ;
     public AccountBean showBalance(int accountId) throws Exception;
       

}
