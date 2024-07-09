package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.FieldDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.FieldModelInt;
import in.co.rays.project_3.model.ModelFactory;

import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "FieldCtl", urlPatterns = { "/ctl/FieldCtl" })

public class FieldCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("String", "String");
		map.put("Integer", "Integer");
		map.put("Long", "Long");
		map.put("double", "double");
		map.put("float", "float");
		map.put("boolean", "boolean");

		
		request.setAttribute("typee", map);
		
		  
		  HashMap map1 = new HashMap();
			map1.put("Yes", "Yes");
			map1.put("No", "No");
			request.setAttribute("activee", map1);
			
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

		if (DataValidator.isNull(request.getParameter("label"))) {
			request.setAttribute("label", PropertyReader.getValue("error.require", "label"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("label"))) {
			request.setAttribute("label", "label must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "type"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("type"))) {
			request.setAttribute("type", "type must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("active"))) {
			request.setAttribute("active", PropertyReader.getValue("error.require", "active"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("active"))) {
			request.setAttribute("active", "active must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}


		

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		FieldDTO dto = new FieldDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setLabel(DataUtility.getString(request.getParameter("label")));
		dto.setActive(DataUtility.getString(request.getParameter("active")));
		dto.setType(DataUtility.getString(request.getParameter("type")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		FieldModelInt model = ModelFactory.getInstance().getFieldModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			FieldDTO dto;
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
		FieldModelInt model = ModelFactory.getInstance().getFieldModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FieldDTO dto = (FieldDTO) populateDTO(request);
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

			FieldDTO dto = (FieldDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.FIELD_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FIELD_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FIELD_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FIELD_VIEW;
	}

}
