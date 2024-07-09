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
import in.co.rays.project_3.dto.VehicleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.VehicleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "VehicleListCtl", urlPatterns = { "/ctl/VehicleListCtl" })

public class VehicleListCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap map = new HashMap();
		map.put("MP04CC5326", "MP04CC5326");
		map.put("MP09HK1238", "MP09HK1238");
		map.put("MP17SD8203", "MP17SD8203");
		map.put("MP11CC6126", "MP11CC6126");
		map.put("MP13CC5922", "MP13CC5922");
		map.put("MP02SD9409", "MP02SD9409");
		map.put("MP10CC0043", "MP10CC0043");

		request.setAttribute("numberr", map);
		
				

	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		VehicleDTO dto = new VehicleDTO();

		dto.setColour(DataUtility.getString(request.getParameter("colour")));
		dto.setInsuranceAmount(DataUtility.getInt(request.getParameter("insuranceAmount")));
		dto.setNumber(DataUtility.getString(request.getParameter("number")));

        dto.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));

		populateBean(dto, request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		VehicleDTO dto = (VehicleDTO) populateDTO(request);

		VehicleModelInt model = ModelFactory.getInstance().getVehicleModel();
		try {
			list = model.search(dto, pageNo, pageSize);

			ArrayList a = (ArrayList<VehicleDTO>) list;

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
		VehicleDTO dto = (VehicleDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		VehicleModelInt model = ModelFactory.getInstance().getVehicleModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (request.getParameter("colour").equals("") && request.getParameter("insuranceAmount").equals("")
						&& request.getParameter("number").equals("")
						&& request.getParameter("purchaseDate").equals("")) {
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
				ServletUtility.redirect(ORSView.VEHICLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					VehicleDTO deletedto = new VehicleDTO();
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
				ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
				return;
			}
			dto = (VehicleDTO) populateDTO(request);
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
		return ORSView.VEHICLE_LIST_VIEW;
	}


}
