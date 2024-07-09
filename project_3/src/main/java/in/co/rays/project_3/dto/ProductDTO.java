package in.co.rays.project_3.dto;

import java.util.Date;

public class ProductDTO extends BaseDTO {

	private String name;
	private String status;
	private String quality;
	private Date purchesDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public Date getPurchesDate() {
		return purchesDate;
	}

	public void setPurchesDate(Date purchesDate) {
		this.purchesDate = purchesDate;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name ;
	}

}
