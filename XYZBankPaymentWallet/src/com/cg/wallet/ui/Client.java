import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import com.cg.wallet.bean.AccountBean;
import com.cg.wallet.bean.CustomerBean;
import com.cg.wallet.bean.WalletTransaction;
import com.cg.wallet.exception.CustomerException;
import com.cg.wallet.service.IWalletService;
import com.cg.wallet.service.WalletServiceImpl;


public class Client {
	   
	IWalletService service=new WalletServiceImpl();
	CustomerBean customer=new CustomerBean();
	Scanner scanner=new Scanner(System.in);
	
	
	public static void main(String[] args) throws Exception {
		char ch;
		Client client=new  Client();
		do
		{
		System.out.println("========Payment wallet application========");
		System.out.println("\t1. Create Account ");
		System.out.println("\t2. Show Balance ");
		System.out.println("\t3. Deposit ");
		System.out.println("\t4. Withdraw ");
		System.out.println("\t5. Fund Transfer");
		System.out.println("\t6. Print Transactions");
		System.out.println("\t7. Exit");
		System.out.println("===========================================");
		System.out.print("Choose an option:\t");
		int option =client.scanner.nextInt();
		
			switch (option) {
			case 1:
				client.create();
				break;
			case 2:
				client.showbalance();

				break;

			case 3:
				client.deposit();

				break;

			case 4:
				client.withdraw();

				break;

			case 5:
				client.fundtransfer();

				break;

			case 6:
				client.printTransaction();

				break;
			case 7:
				System.exit(0);

				break;

			default:
				System.out.println("invalid option");
				break;
			}
		
		System.out.println("\nDo you want to continue press Y/N:\t");
		ch=client.scanner.next().charAt(0);
		
		}while(ch=='y' || ch=='Y');

		
	}
	
	
	void create() throws Exception
	{
		
		System.out.print("Enter Customer firstname:\t");
		String fname=scanner.next();
		
		System.out.print("Enter Customer lastname:\t");
		String lname=scanner.next();
		
		System.out.print("Enter  Customer  email id:\t");
		String email=scanner.next();
		
		System.out.print("Enter  Customer  phone number:\t");
		String phone=scanner.next();
		
		System.out.print("Enter  Customer PAN number:\t");
		String pan=scanner.next();
		
		System.out.print("Enter  Customer  address:\t");
		String address=scanner.next();
		
		
		CustomerBean customerBean=new CustomerBean();
		customerBean.setAddress(address);
		customerBean.setEmailId(email);
		customerBean.setPanNum(pan);
		customerBean.setPhoneNo(phone);
		customerBean.setFirstName(fname);
		customerBean.setLastName(lname);
		
		Random rand = new Random();
	int	accId = rand.nextInt(90000000) + 1000000000;
	
	LocalDateTime ldt = LocalDateTime.now();
	DateTimeFormatter f = DateTimeFormatter.ofPattern("dd MMMM yyyy hh:mm a");
	String accDateInput = ldt.format(f);
	
		System.out.print("Enter balance to create account:\t");
		double balance=scanner.nextDouble();
		
		AccountBean accountBean=new AccountBean();
		accountBean.setAccountId(accId);
		accountBean.setBalance(balance);
		accountBean.setInitialDeposit(balance);
		accountBean.setCustomerBean(customerBean);;
		accountBean.setDateOfOpening(accDateInput);

		boolean result=service.createAccount(accountBean);
		//System.out.println(result);
		if(result)
		{
			System.out.println("\n\n\t\tCongratulations Customer account has been created successfully...!!!");
			System.out.print("\t\tYour Accound ID is :\t\t");
			System.out.println(accountBean.getAccountId());
			System.out.println("\t\tAccount created at "+accDateInput);
			
	
		}
		else
		{
			System.err.println("Enter valid details..!!");
		}
	}
	
	
	void showbalance() throws CustomerException, Exception 
	{
		System.out.print("Enter Account ID:\t");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.showBalance(accId);
		
		
		double balance=accountBean.getBalance();
				
		System.out.println("\n\n\t\tHello "+accountBean.getCustomerBean().getFirstName()+" "+accountBean.getCustomerBean().getLastName()+" !!");
		System.out.print("\t\tYour existing balance is: " +balance);
		System.out.println("\t\tAccount created on : "+accountBean.getDateOfOpening());
		
			
		
	}
	
	void deposit() throws Exception
	{
		System.out.print("Enter Account ID:\t");
		int accId=scanner.nextInt();

		System.out.print("Enter amount that you want to deposit:\t");
		double depositAmt=scanner.nextDouble();
		
		AccountBean accountBean=service.deposit(accId, depositAmt);
		
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(1);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(depositAmt);
		wt.setBeneficiaryAccountBean(null);
		
		accountBean.addTransation(wt);
		
		
		
		
		
		if(accountBean==null)
		{
			System.out.println("Account Does not exist");
			return ;
		}
		
		
		boolean result=service.deposit(accountBean, depositAmt);
		
		
		if(result){
			System.out.println("\n\n\t\tDeposited Money into Account..!!!\n\n ");
		}else{
			System.out.println("\n\n\t\tMoney not deposited into Account..!! ");
		}
			
	}
	
	void withdraw() throws Exception
	{
		System.out.print("Enter Account ID:\t");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		System.out.print("Enter amount that you want to withdraw:\t");
		double withdrawAmt=scanner.nextDouble();
		
		
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(2);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(withdrawAmt);
		wt.setBeneficiaryAccountBean(null);
		
		accountBean.addTransation(wt);
		
		
		if(accountBean==null){
			System.out.println("Account Does not exist..!!");
			return ;
		}
		
		
		boolean result=service.deposit(accountBean, withdrawAmt);
		if(result){
			System.out.println("\n\n\t\tWithdaw Money from Account done..!!");
		}else{
			System.out.println("\n\n\t\tWithdaw Money from Account -Failed!! ");
		}
		
	}
	
	void fundtransfer() throws Exception
	{
		System.out.print("Enter Account ID to Transfer Money From:\t");
		int srcAccId=scanner.nextInt();
		
		AccountBean accountBean1=service.findAccount(srcAccId);
		
		
		System.out.print("Enter Account ID to Transfer Money to:\t");
		int targetAccId=scanner.nextInt();
		
		AccountBean accountBean2=service.findAccount(targetAccId);
		
		System.out.println("Enter amount that you want to transfer:\t");
		double transferAmt=scanner.nextDouble();
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(3);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(transferAmt);
		wt.setBeneficiaryAccountBean(accountBean2);
		
		accountBean1.addTransation(wt);
		
		WalletTransaction wt1=new WalletTransaction();
		wt1.setTransactionType(1);
		wt1.setTransactionDate(new Date());
		wt1.setTransactionAmt(transferAmt);
		wt1.setBeneficiaryAccountBean(accountBean2);
		
		accountBean2.addTransation(wt1);
		
		boolean result=service.fundTransfer(accountBean1, accountBean2, transferAmt);
		
		if(result){
			System.out.println("\n\n\t\tTransfering Money from Account done..!!");
		}else{
			System.out.println("\n\n\t\tTransfering Money from Account Failed..!! ");
		}
		
	}
	
	
	void printTransaction() throws Exception
	{
		System.out.print("Enter Account ID (for printing Transaction Details):\t");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		List<WalletTransaction>  transactions=accountBean.getAllTransactions();
		
		System.out.println(accountBean);
		System.out.println(accountBean.getCustomerBean());

		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("Transaction Type\t\tTransaction Date\t\tTransaction Amount");
		System.out.println("----------------------------------------------------------------------------------------");

		for(WalletTransaction wt:transactions){
			
			String str="";
			if(wt.getTransactionType()==1){
				str=str+"DEPOSIT ";
			}
			if(wt.getTransactionType()==2){
				str=str+"WITHDRAW";
			}
			if(wt.getTransactionType()==3){
				str=str+"FUND TRANSFER";
			}
			
			str=str+"\t\t\t"+wt.getTransactionDate();
			
			str=str+"\t\t\t"+wt.getTransactionAmt();
			System.out.println(str);
		}
		
		System.out.println("----------------------------------------------------------------------------------------");
	
	}
	
	    
	
}
