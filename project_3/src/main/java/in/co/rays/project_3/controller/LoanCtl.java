package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LoanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.LoanModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "LoanCtl", urlPatterns = { "/ctl/LoanCtl" })

public class LoanCtl extends BaseCtl{
	
	
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap map = new HashMap();

		map.put("171724", "171724");
		map.put("091234", "091234");
		map.put("074532", "074532");
		map.put("029821", "029821");
		map.put("175666", "175666");
		map.put("029821", "029821");
		map.put("175326", "175326");
		map.put("092821", "099821");
		map.put("145326", "145326");

		request.setAttribute("customer_idd", map);

	}

	
	
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("customer_id"))) {
			request.setAttribute("customer_id", PropertyReader.getValue("error.require", "customer_id"));
			pass = false;
		}

		
			

		if (DataValidator.isNull(request.getParameter("loan_amount"))) {
			request.setAttribute("loan_amount", PropertyReader.getValue("error.require", " loan_amount"));

			pass = false;
		

		}
		if (DataValidator.isNull(request.getParameter("interest_rate"))) {
			request.setAttribute("interest_rate", PropertyReader.getValue("error.require", "interest_rate"));
			pass = false;
		}

		

				  
		
		if (DataValidator.isNull(request.getParameter("loan_start_date"))) {
			request.setAttribute("loan_start_date", PropertyReader.getValue("error.require", "loan_start_date"));
			pass = false;
		}

		

		 

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		LoanDTO dto = new LoanDTO();


		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setLoan_amount(DataUtility.getInt(request.getParameter("loan_amount")));
		dto.setInterest_rate(DataUtility.getDouble(request.getParameter("interest_rate")));
		dto.setCustomer_id(DataUtility.getLong(request.getParameter("customer_id")));

        dto.setLoan_start_date(DataUtility.getDate(request.getParameter("loan_start_date")));



		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		LoanModelInt model = ModelFactory.getInstance().getLoanModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			LoanDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		LoanModelInt model = ModelFactory.getInstance().getLoanModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			LoanDTO dto = (LoanDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			LoanDTO dto = (LoanDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.LOAN_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LOAN_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LOAN_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LOAN_VIEW;
	}


}
