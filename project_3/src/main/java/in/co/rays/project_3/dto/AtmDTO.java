package in.co.rays.project_3.dto;

import java.util.Date;

public class AtmDTO extends BaseDTO{
	
	private String location;
	private double cash_available;
	private Date last_restocked_date;
	private String remark;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getCash_available() {
		return cash_available;
	}
	public void setCash_available(double cash_available) {
		this.cash_available = cash_available;
	}
	public Date getLast_restocked_date() {
		return last_restocked_date;
	}
	public void setLast_restocked_date(Date last_restocked_date) {
		this.last_restocked_date = last_restocked_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
