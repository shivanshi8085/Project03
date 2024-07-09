package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.WishDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ProjectModelInt;
import in.co.rays.project_3.model.WishModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;


@WebServlet(name = "WishCtl", urlPatterns = { "/ctl/WishCtl" })

public class WishCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("Table", "Table");
		map.put("Bag", "Bag");
		map.put("Paper", "Paper");
		map.put("Mobile", "Mobile");
		map.put("Laptop", "Laptop");
		map.put("Book", "Book");

		request.setAttribute("productt", map);
		
	}
	
	
	
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		
		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "userName"));
			pass = false;
		}

		
		if (DataValidator.isNull(request.getParameter("remark"))) {
			request.setAttribute("remark", PropertyReader.getValue("error.require", "remark"));
			pass = false;
		} 

		
		
		if (DataValidator.isNull(request.getParameter("product"))) {
			request.setAttribute("product", PropertyReader.getValue("error.require", "product"));
			pass = false;
		}

		
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", " date"));

			pass = false;
		} 
		
		return pass;

		
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		WishDTO dto = new WishDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setUserName(DataUtility.getString(request.getParameter("userName")));
		dto.setProduct(DataUtility.getString(request.getParameter("product")));
		dto.setRemark(DataUtility.getString(request.getParameter("remark")));
        dto.setDate(DataUtility.getDate(request.getParameter("date")));


		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		WishModelInt model = ModelFactory.getInstance().getWishModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			WishDTO dto;
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
		WishModelInt model = ModelFactory.getInstance().getWishModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			WishDTO dto = (WishDTO) populateDTO(request);
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

			WishDTO dto = (WishDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.WISH_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WISH_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WISH_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.WISH_VIEW;
	}


			

}
