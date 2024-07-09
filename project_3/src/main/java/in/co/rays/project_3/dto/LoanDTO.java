package in.co.rays.project_3.dto;

import java.util.Date;

public class LoanDTO  extends BaseDTO {
	
	private Date loan_start_date;
	private int loan_amount;
	private long customer_id;
	private double interest_rate;
	
	public Date getLoan_start_date() {
		return loan_start_date;
	}
	public void setLoan_start_date(Date loan_start_date) {
		this.loan_start_date = loan_start_date;
	}
	public long getLoan_amount() {
		return loan_amount;
	}
	public void setLoan_amount(int loan_amount) {
		this.loan_amount = loan_amount;
	}
		public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	
	public double getInterest_rate() {
		return interest_rate;
	}
	public void setInterest_rate(double interest_rate) {
		this.interest_rate = interest_rate;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
