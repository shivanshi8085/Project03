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
import in.co.rays.project_3.dto.LoanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.LoanModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "LoanListCtl", urlPatterns = { "/ctl/LoanListCtl" })

public class LoanListCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap map = new HashMap();

		map.put("171724", "171724");
		map.put("091234", "091234");
		map.put("074532", "074532");
		map.put("029821", "029821");
		map.put("175666", "175666");
		map.put("029821", "029821");
		map.put("175326", "175326");
		map.put("092821", "099821");
		map.put("145326", "145326");

		request.setAttribute("customer_idd", map);

	}
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		LoanDTO dto = new LoanDTO();

		dto.setLoan_amount(DataUtility.getInt(request.getParameter("loan_amount")));
		dto.setInterest_rate(DataUtility.getDouble(request.getParameter("interest_rate")));
		dto.setCustomer_id(DataUtility.getLong(request.getParameter("customer_id")));

        dto.setLoan_start_date(DataUtility.getDate(request.getParameter("loan_start_date")));


		populateBean(dto, request);
		return dto;
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		LoanDTO dto = (LoanDTO) populateDTO(request);

		LoanModelInt model = ModelFactory.getInstance().getLoanModel();
		try {
			list = model.search(dto, pageNo, pageSize);

			ArrayList a = (ArrayList<LoanDTO>) list;

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
		LoanDTO dto = (LoanDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		LoanModelInt model = ModelFactory.getInstance().getLoanModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (request.getParameter("customer_id").equals("") && request.getParameter("loan_amount").equals("")
						&& request.getParameter("interest_rate").equals("")
						&& request.getParameter("loan_start_date").equals("")) {
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
				ServletUtility.redirect(ORSView.LOAN_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.LOAN_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					LoanDTO deletedto = new LoanDTO();
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
				ServletUtility.redirect(ORSView.LOAN_LIST_CTL, request, response);
				return;
			}
			dto = (LoanDTO) populateDTO(request);
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
		return ORSView.LOAN_LIST_VIEW;
	}
	
	

}
