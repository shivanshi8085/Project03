package in.co.rays.project_3.dto;

import java.util.Date;

public class SalaryDTO  extends BaseDTO{
	
	private int amount;
	private Date appliedDate;
	private String status;
	private String mobileNumber;
	private String description;
	
	
	
	

		public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

		public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
