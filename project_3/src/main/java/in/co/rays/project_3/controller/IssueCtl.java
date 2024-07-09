package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.IssueDTO;

import in.co.rays.project_3.dto.IssueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.IssueModelInt;
import in.co.rays.project_3.model.ModelFactory;

import in.co.rays.project_3.model.ProductModelInt;
import in.co.rays.project_3.model.UserModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "IssueCtl", urlPatterns = { "/ctl/IssueCtl" })

public class IssueCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("Open", "Open");
		map.put("In Progress", "In Progress");
		map.put("Hold", "Hold");
		map.put("Resolve", "Resolve");
		map.put("Close", "Close");
		
		request.setAttribute("statuss", map);
	}

	

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("assinTo"))) {
			request.setAttribute("assinTo", PropertyReader.getValue("error.require", "assinTo"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("assinTo"))) {
			request.setAttribute("assinTo", "assinTo must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "title must contains alphabets only");
			System.out.println(pass);
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

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("description"))) {
			request.setAttribute("description", "description must contains alphabets only");
			System.out.println(pass);
			pass = false;

		}
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
		IssueDTO dto = new IssueDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAssinTo(DataUtility.getString(request.getParameter("assinTo")));
		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setOpenDate(DataUtility.getDate(request.getParameter("openDate")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		IssueModelInt model = ModelFactory.getInstance().getIssueModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			IssueDTO dto;
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
		IssueModelInt model = ModelFactory.getInstance().getIssueModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			IssueDTO dto = (IssueDTO) populateDTO(request);
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

			IssueDTO dto = (IssueDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ISSUE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ISSUE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ISSUE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ISSUE_VIEW;
	}

}
