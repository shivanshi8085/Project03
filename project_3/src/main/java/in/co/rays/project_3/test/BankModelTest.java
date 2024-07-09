package in.co.rays.project_3.test;

import java.util.Date;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BankModelInt;
import in.co.rays.project_3.model.ModelFactory;

public class BankModelTest {
	
	public static void main(String[] args) throws Exception {
		
		testAdd();
	}

	private static void testAdd() throws Exception, Exception {
		BankDTO dto= new BankDTO();
		
		dto.setName("shivanshi");
		dto.setDob(new Date());
		dto.setAccountNumber("12345");
		dto.setAddress("Rewa");
		
		BankModelInt bankModel = ModelFactory.getInstance().getBankModel();
		bankModel.add(dto);
		
		
		
	}

}
