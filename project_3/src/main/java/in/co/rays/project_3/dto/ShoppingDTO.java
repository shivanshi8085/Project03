package in.co.rays.project_3.dto;

import java.util.Date;

public class ShoppingDTO extends BaseDTO{
	
	private String name;
	private String product;
	private Date date;
	private int quantity;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
