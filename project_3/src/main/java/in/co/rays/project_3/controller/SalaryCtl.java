package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SalaryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.SalaryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.UserModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SalaryCtl", urlPatterns = { "/ctl/SalaryCtl" })

public class SalaryCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("Open", "Open");
		map.put("Hold", "Hold");
		map.put("Close", "Close");
		request.setAttribute("statuss", map);
		
		 	}
	
	

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("description"))) {
			request.setAttribute("description", "description must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("mobileNumber"))) {
			request.setAttribute("mobileNumber", PropertyReader.getValue("error.require", "mobileNumber"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNumber"))) {
			request.setAttribute("mobileNumber", "Mobile No. Series start with 6-9 ");
			if (!DataValidator.isPhoneLength(request.getParameter("mobileNumber"))) {
				request.setAttribute("mobileNumber", "mobileNumber No.must be 10 digit ");
			}
			pass = false;
		}
		
	else if (!DataValidator.isPhoneLength(request.getParameter("mobileNumber"))) {
		request.setAttribute("mobileNumber", "mobileNumber No.must be 10 digit ");
		
		
		pass = false;
	}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("status"))) {
			request.setAttribute("status", "status must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("appliedDate"))) {
			request.setAttribute("appliedDate", PropertyReader.getValue("error.require", " appliedDate"));

			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("appliedDate"))) {
			request.setAttribute("appliedDate", " appliedDate must contains Date only");
			System.out.println(pass);
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "amount"));
			pass = false;
		}

		else if (!DataValidator.isInteger(request.getParameter("amount"))) {
			request.setAttribute("amount", "amount must contains digit only");
			System.out.println(pass);
			pass = false;

		}		  
		 

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		SalaryDTO dto = new SalaryDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setAmount(DataUtility.getInt(request.getParameter("amount")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));
        dto.setAppliedDate(DataUtility.getDate(request.getParameter("appliedDate")));
		dto.setMobileNumber(DataUtility.getString(request.getParameter("mobileNumber")));



		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		SalaryModelInt model = ModelFactory.getInstance().getSalaryModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			SalaryDTO dto;
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
		SalaryModelInt model = ModelFactory.getInstance().getSalaryModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SalaryDTO dto = (SalaryDTO) populateDTO(request);
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

			SalaryDTO dto = (SalaryDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.SALARY_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SALARY_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SALARY_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SALARY_VIEW;
	}

}
