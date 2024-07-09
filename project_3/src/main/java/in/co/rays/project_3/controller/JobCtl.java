package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.JobModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.UserModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "JobCtl", urlPatterns = { "/ctl/JobCtl" })

public class JobCtl extends BaseCtl {

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

		if (DataValidator.isNull(request.getParameter("experience"))) {
			request.setAttribute("experience", PropertyReader.getValue("error.require", "experience"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("experience"))) {
			request.setAttribute("experience", "assinTo must contains alphabets only");
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

		if (DataValidator.isNull(request.getParameter("openingJob"))) {
			request.setAttribute("openingJob", PropertyReader.getValue("error.require", " openingJob"));

			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("openingJob"))) {
			request.setAttribute("openDate", " name must contains Date only");
			System.out.println(pass);
			pass = false;

		}

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		JobDTO dto = new JobDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setExperience(DataUtility.getString(request.getParameter("experience")));
		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		dto.setOpeningJob(DataUtility.getDate(request.getParameter("openingJob")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		JobModelInt model = ModelFactory.getInstance().getJobModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			JobDTO dto;
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
		JobModelInt model = ModelFactory.getInstance().getJobModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			JobDTO dto = (JobDTO) populateDTO(request);
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

			JobDTO dto = (JobDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.JOB_VIEW;
	}

}
