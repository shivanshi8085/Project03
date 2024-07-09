package in.co.rays.project_3.dto;

import java.util.Date;

public class RouteDTO extends BaseDTO{
	
	private String name;
	private Date startDate;
	private int allowedSpeed;
	private String vehicleId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getAllowedSpeed() {
		return allowedSpeed;
	}
	public void setAllowedSpeed(int allowedSpeed) {
		this.allowedSpeed = allowedSpeed;
	}
	
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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
