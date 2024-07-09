package in.co.rays.project_3.dto;

import java.util.Date;

public class IssueDTO extends BaseDTO{
	
	
	private Date openDate;
	private String title;
	private String description;
	private String assinTo;
	private String status;
	
	

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssinTo() {
		return assinTo;
	}

	public void setAssinTo(String assinTo) {
		this.assinTo = assinTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
