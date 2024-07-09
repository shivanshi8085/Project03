package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DoctorDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.DoctorModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PatientModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "DoctorListCtl", urlPatterns = { "/ctl/DoctorListCtl" })

public class DoctorListCtl extends BaseCtl{
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("Anesthesiologist", "Anesthesiologist");
		map.put("Cardiologist", "Cardiologist");
		map.put("Dermatologist", "Dermatologist");
		map.put("Endocrinologist", "Endocrinologist");
		map.put("Gastroenterologist", "Gastroenterologist");
		map.put("Hematologist", "Hematologist");
		map.put("Neurologist", "Neurologist");
		map.put("Neurosurgeon", "Neurosurgeon");
		map.put("Gynecologist", "Gynecologist");
		map.put("Pathologist", "Pathologist");
		
		request.setAttribute("expertiess", map);
		 	}


	
	
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		DoctorDTO dto = new DoctorDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setExperties(DataUtility.getString(request.getParameter("experties")));
		dto.setMobile(DataUtility.getString(request.getParameter("mobile")));

        dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		populateBean(dto, request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		DoctorDTO dto = (DoctorDTO) populateDTO(request);

		DoctorModelInt model = ModelFactory.getInstance().getDoctorModel();
		try {
			list = model.search(dto, pageNo, pageSize);

			ArrayList a = (ArrayList<DoctorDTO>) list;

			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		DoctorDTO dto = (DoctorDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		DoctorModelInt model = ModelFactory.getInstance().getDoctorModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (request.getParameter("name").equals("") && request.getParameter("experties").equals("")
						&& request.getParameter("mobile").equals("")
						&& request.getParameter("dob").equals("")) {
					ServletUtility.setErrorMessage("fill  at least one field", request);
				}

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					DoctorDTO deletedto = new DoctorDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
				return;
			}
			dto = (DoctorDTO) populateDTO(request);
			list = model.search(dto, pageNo, pageSize);

			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				if (!OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.DOCTOR_LIST_VIEW;
	}


}
