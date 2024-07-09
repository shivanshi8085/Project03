package in.co.rays.project_3.dto;

import java.util.Date;

public class JobDTO extends BaseDTO{
	
	private String title;
	private Date openingJob;
	private String status;
	private String experience;
	
	

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getOpeningJob() {
		return openingJob;
	}

	public void setOpeningJob(Date openingJob) {
		this.openingJob = openingJob;
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
		return status;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return status;
	}

}
