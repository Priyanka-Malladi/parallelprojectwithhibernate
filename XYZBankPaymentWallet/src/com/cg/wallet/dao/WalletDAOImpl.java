package com.cg.wallet.dao;

import javax.persistence.EntityManager;

import com.cg.wallet.dao.JPAManager;
import com.cg.wallet.bean.AccountBean;
import com.cg.wallet.exception.CustomerException;

public class WalletDAOImpl implements IWalletDAO {

	private EntityManager em;
	@Override
	public boolean createAccount(AccountBean accountBean)
			throws CustomerException {
		// TODO Auto-generated method stub
		boolean isValid=insert(accountBean);
		return isValid;
	}
	@Override
	public AccountBean showBalance(int accountId) throws Exception {
		// TODO Auto-generated method stub
		AccountBean accountBean=findAccount(accountId);
		
		if(accountBean==null){
			System.out.println("Account Does not exist..!!");
			return null;
		}
		
		double balance=accountBean.getBalance();
		return accountBean;
				
		
	}
	 
	@Override
	public boolean deposit(AccountBean accountBean, double depositAmount)
			throws Exception {
		// TODO Auto-generated method stub
		boolean result=false;
		if(depositAmount>0){
			accountBean.setBalance(accountBean.getBalance()+depositAmount);
			result=updateAccount(accountBean);
		}
		return result;
	}

	@Override
	public boolean withdraw(AccountBean accountBean, double withdrawAmount)
			throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		if(withdrawAmount>0 && withdrawAmount<=accountBean.getBalance()){
		accountBean.setBalance(accountBean.getBalance()-withdrawAmount);
		result=updateAccount(accountBean);
		}
		return result;
	}

	@Override
	public boolean fundTransfer(AccountBean transferingAccountBean,
			AccountBean beneficiaryAccountBean, double transferAmount)
			throws Exception {
		// TODO Auto-generated method stub
		boolean result1=false;
		boolean result2=false;
		if(transferAmount>0){
		transferingAccountBean.setBalance(transferingAccountBean.getBalance()-transferAmount);
		beneficiaryAccountBean.setBalance(beneficiaryAccountBean.getBalance()+transferAmount);
		result1=updateAccount(transferingAccountBean);
		result2=updateAccount(beneficiaryAccountBean);
		}
		return result1 && result2;
	}

    private boolean insert(AccountBean accountBean) throws CustomerException {
		
    	this.em=JPAManager.createEntityManager();
		em.getTransaction().begin();
		em.persist(accountBean);
		em.getTransaction().commit();
		JPAManager.closeResources(em);
		return true;
    	
    }
	 private boolean updateAccount(AccountBean accountBean) throws Exception{
		 try{
				this.em=JPAManager.createEntityManager();
				em.getTransaction().begin();
				
				em.merge(accountBean);
				
				em.getTransaction().commit();
				JPAManager.closeResources(em);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		
		 
	 }

		private AccountBean findAccount(int accountId) throws Exception {
			// TODO Auto-generated method stub
			try{
				em=JPAManager.createEntityManager();
				AccountBean accountBean2=em.find(AccountBean.class,accountId);
				JPAManager.closeResources(em);
				return accountBean2;
				
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
}


	 
