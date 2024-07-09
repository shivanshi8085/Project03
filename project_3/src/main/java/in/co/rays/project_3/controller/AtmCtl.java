package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AtmDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AtmModelInt;
import in.co.rays.project_3.model.ModelFactory;

import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AtmCtl", urlPatterns = { "/ctl/AtmCtl" })

public class AtmCtl extends BaseCtl { 
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("Bholaram", "Bholaram");
		map.put("Madhumilan", "Madhumilan");
		map.put("Navlakha", "Navlakha");
		map.put("RajandraNager", "RajandraNager");
		map.put("VijayNager", "VijayNager");
		map.put("SudamaNager", "SudamaNager");
		map.put("Bhawarkua", "Bhawarkua");

		request.setAttribute("locationn", map);
		
		 	}
	
	
	
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("remark"))) {
			request.setAttribute("remark", PropertyReader.getValue("error.require", "remark"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("remark"))) {
			request.setAttribute("remark", "remark must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}
				if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "location"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("location"))) {
			request.setAttribute("location", "location must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("last_restocked_date"))) {
			request.setAttribute("last_restocked_date", PropertyReader.getValue("error.require", " last_restocked_date"));

			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("last_restocked_date"))) {
			request.setAttribute("last_restocked_date", " last_restocked_date must contains Date only");
			System.out.println(pass);
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("cash_available"))) {
			request.setAttribute("cash_available", PropertyReader.getValue("error.require", "cash_available"));
			pass = false;
		}

		
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		AtmDTO dto = new AtmDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRemark(DataUtility.getString(request.getParameter("remark")));
		dto.setCash_available(DataUtility.getDouble(request.getParameter("cash_available")));
		dto.setLocation(DataUtility.getString(request.getParameter("location")));
        dto.setLast_restocked_date(DataUtility.getDate(request.getParameter("last_restocked_date")));



		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		AtmModelInt model = ModelFactory.getInstance().getAtmModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			AtmDTO dto;
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
		AtmModelInt model = ModelFactory.getInstance().getAtmModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			AtmDTO dto = (AtmDTO) populateDTO(request);
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

			AtmDTO dto = (AtmDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ATM_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ATM_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ATM_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ATM_VIEW;
	}



}
