package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.OrderModelInt;
import in.co.rays.project_3.model.ProductModelInt;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "OrderCtl", urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl {

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

		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", " amount"));

			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", PropertyReader.getValue("error.require", "quantity"));
			
			pass = false;
			
		}

			if (DataValidator.isNull(request.getParameter("product"))) {
				request.setAttribute("product", PropertyReader.getValue("error.require", "product"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("date"))) {
				request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
				pass = false;

			}

		
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		OrderDTO dto = new OrderDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAmount(DataUtility.getLong(request.getParameter("amount")));
		dto.setQuantity(DataUtility.getInt(request.getParameter("quantity")));
		dto.setProduct(DataUtility.getString(request.getParameter("product")));
		dto.setDate(DataUtility.getDate(request.getParameter("date")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		OrderModelInt model = ModelFactory.getInstance().getOrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			OrderDTO dto;
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
		OrderModelInt model = ModelFactory.getInstance().getOrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			OrderDTO dto = (OrderDTO) populateDTO(request);
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

			OrderDTO dto = (OrderDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ORDER_VIEW;
	}

}
