package com.cg.wallet.service;
import com.cg.wallet.bean.AccountBean;
import com.cg.wallet.dao.IWalletDAO;
import com.cg.wallet.dao.WalletDAOImpl;
import com.cg.wallet.exception.CustomerException;
import com.cg.wallet.exception.CustomerExceptionMessage;

public class WalletServiceImpl implements IWalletService {
	
	IWalletDAO dao=new WalletDAOImpl();
		@Override
		public boolean createAccount(AccountBean accountBean)
				throws CustomerException {
			boolean result=false;
			if(validate(accountBean)){
			 result=dao.createAccount(accountBean);
			}
			 return result;
		}

		private boolean validate(AccountBean accountBean) throws CustomerException {
			// TODO Auto-generated method stub
			boolean valid=true;
			if(!(accountBean.getCustomerBean().getFirstName().matches("[A-Za-z]{4,}"))) {
				throw new CustomerException(CustomerExceptionMessage.ERROR1);
			}
			if(!(accountBean.getCustomerBean().getLastName().matches("[A-Za-z]{4,}"))) {
				throw new CustomerException(CustomerExceptionMessage.ERROR2);
			}
			if(!(accountBean.getCustomerBean().getPhoneNo().matches("^[6-9][0-9]{9}$"))) {
				throw new CustomerException(CustomerExceptionMessage.ERROR5);
			}
			if(!(accountBean.getCustomerBean().getEmailId().matches("[a-zA-Z0-9]+@+[a-z]+\\.com"))) {
				throw new CustomerException(CustomerExceptionMessage.ERROR3);
			}
			if(!(accountBean.getBalance()>500)) {
				throw new CustomerException(CustomerExceptionMessage.ERROR6);
			}
			if(!(accountBean.getCustomerBean().getAddress()!=null)) {
				throw new CustomerException(CustomerExceptionMessage.ERROR7);
			}
			if(!(accountBean.getCustomerBean().getPanNum().length()==10)) {
				throw new CustomerException(CustomerExceptionMessage.ERROR4);
			}
			return valid;
		}



		@Override
		public boolean deposit(AccountBean accountBean, double depositAmount)
				throws Exception {
			return dao.deposit(accountBean, depositAmount);
			
		}

		@Override
		public boolean withdraw(AccountBean accountBean, double withdrawAmount)
				throws Exception {
			return dao.withdraw(accountBean, withdrawAmount);
			
		}

		@Override
		public boolean fundTransfer(AccountBean transferingAccountBean,
				AccountBean beneficiaryAccountBean, double transferAmount) throws Exception {
			
			transferingAccountBean.setBalance(transferingAccountBean.getBalance()-transferAmount);
			beneficiaryAccountBean.setBalance(beneficiaryAccountBean.getBalance()+transferAmount);
			
			boolean result1=dao.updateAccount(transferingAccountBean);
			boolean result2=dao.updateAccount(beneficiaryAccountBean);
			return result1 && result2;
		}

		

		@Override
		public AccountBean showBalance(int accountId) throws Exception {
			// TODO Auto-generated method stub
			return dao.showBalance(accountId);
		}

	}



