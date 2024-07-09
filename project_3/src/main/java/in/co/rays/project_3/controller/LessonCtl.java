package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LessonDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.LessonModelInt;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "LessonCtl", urlPatterns = { "/ctl/LessonCtl" })
public class LessonCtl extends BaseCtl{
	
	protected void preload(HttpServletRequest request) {
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		try {
			List list = model.list();
			request.setAttribute("roleList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
					
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", " name"));
			
			pass = false;
			
			  } else if (!DataValidator.isName(request.getParameter("name"))) {
			  request.setAttribute("name", " name must contains alphabets only");
			  System.out.println(pass); pass = false;
			 
		}
		if (DataValidator.isNull(request.getParameter("chapterNo"))) {
			request.setAttribute("chapterNo", PropertyReader.getValue("error.require", "chapterNo"));
			System.out.println(pass);
			pass = false;
			/*
			 * } else if (!DataValidator.isName(request.getParameter("status"))) {
			 * request.setAttribute("chapterNo", "chapterNo must contains alphabets only");
			 * System.out.println(pass); pass = false;
			 */

		}
		if (!OP_UPDATE.equalsIgnoreCase(request.getParameter("operation"))) {
			
			if(DataValidator.isNull(request.getParameter("subject"))) {
			  request.setAttribute("subject", PropertyReader.getValue("error.require", "subject"));
			  pass = false;
		}
				
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
			pass = false;
		
		}
				
		}
		return pass;
		}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		LessonDTO dto = new LessonDTO();
		
         
         System.out.println(request.getParameter("dob"));      
   
		dto.setId(DataUtility.getLong(request.getParameter("id")));
       	dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setChapterNo(DataUtility.getString(request.getParameter("chapterNo")));
        dto.setSubject(DataUtility.getString(request.getParameter("subject")));
        dto.setDate(DataUtility.getDate(request.getParameter("date")));



        populateBean(dto,request);
		

		return dto;

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		LessonModelInt model = ModelFactory.getInstance().getLessonModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			LessonDTO dto;
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		LessonModelInt model = ModelFactory.getInstance().getLessonModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			LessonDTO dto = (LessonDTO) populateDTO(request);
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

			LessonDTO dto = (LessonDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.LESSON_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LESSON_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LESSON_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LESSON_VIEW;
	}

}
