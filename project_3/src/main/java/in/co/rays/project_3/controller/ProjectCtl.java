package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ProjectDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ProjectModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;


@WebServlet(name = "ProjectCtl", urlPatterns = { "/ctl/ProjectCtl" })

public class ProjectCtl extends BaseCtl {
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("static", "static");
		map.put("dynamic", "dynamic");
		
		request.setAttribute("categoryy", map);
		
	}
	
	
	
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "name must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "category"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("category"))) {
			request.setAttribute("category", "category must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}


		
		
		if (DataValidator.isNull(request.getParameter("version"))) {
			request.setAttribute("version", PropertyReader.getValue("error.require", "version"));
			pass = false;
		}

		/*
		 * else if (!DataValidator.isName(request.getParameter("version"))) {
		 * request.setAttribute("version", "version must digit  only");
		 * System.out.println(pass); pass = false;
		 * 
		 * }
		 * 
		 */
		
		if (DataValidator.isNull(request.getParameter("openDate"))) {
			request.setAttribute("openDate", PropertyReader.getValue("error.require", " openDate"));

			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("openDate"))) {
			request.setAttribute("openDate", " openDate must contains Date only");
			System.out.println(pass);
			pass = false;

		}
		
		
		
				

		

		return pass;

		
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		ProjectDTO dto = new ProjectDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setCategory(DataUtility.getString(request.getParameter("category")));
		dto.setVersion(DataUtility.getString(request.getParameter("version")));


		/*
		 * dto.setDescription(DataUtility.getString(request.getParameter("description"))
		 * );
		 */		dto.setOpenDate(DataUtility.getDate(request.getParameter("openDate")));


		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		ProjectModelInt model = ModelFactory.getInstance().getProjectModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			ProjectDTO dto;
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
		ProjectModelInt model = ModelFactory.getInstance().getProjectModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ProjectDTO dto = (ProjectDTO) populateDTO(request);
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

			ProjectDTO dto = (ProjectDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.PROJECT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PROJECT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PROJECT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}


			
		

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PROJECT_VIEW;
	}

}
